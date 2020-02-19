package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEmailPwd;

import java.sql.Timestamp;

/**
 * @Auther: 陈浩
 * @Date: 2020/1/8 18:58
 * @Description:
 */
public interface EmailPwdService {


    TEmailPwd findTemailPwd(String email, String vcode, Timestamp timestamp);

    boolean update(TEmailPwd tEmailPwd);

}
