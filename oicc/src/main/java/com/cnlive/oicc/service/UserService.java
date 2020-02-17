package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TUser;

public interface UserService {
    /**
     * 获取用户信息 更具名称
     */
    TUser getUserByName(String username);
}
