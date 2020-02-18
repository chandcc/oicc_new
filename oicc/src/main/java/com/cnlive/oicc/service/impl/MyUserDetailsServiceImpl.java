package com.cnlive.oicc.service.impl;
import com.cnlive.oicc.entity.TPermission;
import com.cnlive.oicc.entity.TRole;
import com.cnlive.oicc.entity.TUser;
import com.cnlive.oicc.service.MyUserDetailsService;
import com.cnlive.oicc.service.PermissionService;
import com.cnlive.oicc.service.RoleService;
import com.cnlive.oicc.service.UserService;
import com.cnlive.oicc.utils.MyRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TUser user = userService.getUserByName(username);//获取用户对象
        if(user== null){ //判断，若在数据库查不到该账户的用户信息抛出notfound异常
            throw new UsernameNotFoundException(username);
        }
        List<TRole> Roles=roleService.findRolesList();//获取role表中所以对象
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(TRole role:Roles){
            //获取每种角色下面的资源列表
            List<TPermission> permissionList = permissionService.getPermissionsByRoleCode(role.getId());
            for (TPermission permission:permissionList) {
                authorities.add(new SimpleGrantedAuthority("PRO1"));
            }
        }

        return new User(user.getName(),user.getPassword(),true,true,true,true,authorities);
    }

}
