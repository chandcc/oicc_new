package com.cnlive.oicc.config;

import com.cnlive.oicc.service.MyUserDetailsService;
import com.cnlive.oicc.service.impl.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //声其是配置类
@EnableWebSecurity //开启web 安全框架
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级别的权限验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    //配置忽略拦截的资源,主要是一些静态页面资源和构成页面所必要的js文件
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**");
//    }
    //配置访问控制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        http.authorizeRequests()
                .antMatchers( "/assets/**","/data/**","/fcds/**","/js/**","/api/login").permitAll()
                .anyRequest().authenticated()   // 其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginPage("/api/login")
//                .successHandler(myAuthenticationSuccessHandler) //  登录页
                .failureUrl("/api/500").permitAll() //登录失败页面
                .and()
                .logout()
                .logoutSuccessUrl("/api/login");
    }
    //配置密码校验
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    //创建一个密码 bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
