package com.cnlive.oicc.service.impl;
import com.cnlive.oicc.entity.TPermission;
import com.cnlive.oicc.entity.TRole;
import com.cnlive.oicc.entity.TUser;
import com.cnlive.oicc.service.MyUserDetailsService;
import com.cnlive.oicc.service.PermissionService;
import com.cnlive.oicc.service.UserService;
import com.cnlive.oicc.utils.MyRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private final UserService userService;
    private PermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TUser user = userService.getUserByName(username);//获取用户对象
        if(user== null){ //判断，若在数据库查不到该账户的用户信息抛出notfound异常
            throw new UsernameNotFoundException(username);
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(TRole role:user.getRoles()){
            //获取每种角色下面的资源列表
            List<TPermission> permissionList = permissionService.getPermissionsByRoleCode(role.getRoleCode());
            for (TPermission permission:permissionList) {
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionCode()));
            }
        }

        return new User(user.getName(),user.getPassword(),authorities);
    }

}
