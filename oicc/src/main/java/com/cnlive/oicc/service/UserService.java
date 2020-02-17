package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TUser;

public interface UserService {
    /**
     * 根据用户名获取用户信息
     */
    TUser getUserByName(String username);
}
