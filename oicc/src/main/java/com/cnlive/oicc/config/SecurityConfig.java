package com.cnlive.oicc.config;

import com.cnlive.oicc.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //声其是配置类
@EnableWebSecurity //开启web 安全框架
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级别的权限验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

//    @Autowired
//    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;


   // 配置忽略拦截的资源,主要是一些静态页面资源和构成页面所必要的js文件
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/assets/**","/data/**","/fcds/**","/js/**");
    }
    //创建一个密码 bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    //配置访问控制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
                .antMatchers( "/","/api/login").permitAll()
                .anyRequest().authenticated()
                .and()   // 其他地址的访问均需验证权限
                .formLogin()
                //  登录页
                .loginPage("/api/login")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/api/tologin")
                //登录成功跳转路径
                .defaultSuccessUrl("/production/index/1/0",true)
                .failureUrl("/api/500") //登录失败页面
                .and()
                .logout()
                .logoutSuccessUrl("/api/login");
        //默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        http .csrf().disable();

        /*这里值的注意的是表单的用户名name和password输入框的name=""要和security里面的验证的对应:
        name="username";name="password",否则无法识别,另外action="/authentication/form"要与.loginProcessingUrl("/authentication/form")相对应,
        原因为:由于security是由UsernamePasswordAuthenticationFilter这个类定义登录的,里面默认是/login路径,我们要让他用我们的/authentication/form路径,
        就需要配置.loginProcessingUrl("/authentication/form")*/
    }

    //配置密码校验,从数据库查询
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

     //配置密码校验，从内存中查询
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles("admin");
//    }

}
