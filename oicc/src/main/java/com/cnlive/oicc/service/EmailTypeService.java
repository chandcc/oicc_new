package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEmailType;

import java.sql.Timestamp;

/**
 * @Auther: 陈浩
 * @Date: 2020/1/8 16:25
 * @Description:
 */
public interface EmailTypeService {

    TEmailType findTemailType(String email, String vcode, Timestamp timestamp);

    boolean update(TEmailType tEmailType);
}
