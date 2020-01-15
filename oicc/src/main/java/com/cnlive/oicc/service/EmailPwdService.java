package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEmailPwd;
import com.cnlive.oicc.entity.TEmailType;
import com.cnlive.oicc.mapper.TEmailPwdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Auther: 陈浩
 * @Date: 2020/1/8 18:58
 * @Description:
 */
@Service
public class EmailPwdService {
    @Autowired
    TEmailPwdMapper tEmailPwdMapper;

    public TEmailPwd findTemailPwd(String email, String vcode, Timestamp timestamp) {
        return tEmailPwdMapper.findTemailType(email, vcode, timestamp);
    }

    public boolean update(TEmailPwd tEmailPwd) {
        return tEmailPwdMapper.updateByPrimaryKeySelective(tEmailPwd) > 0 ? true : false;
    }

}
