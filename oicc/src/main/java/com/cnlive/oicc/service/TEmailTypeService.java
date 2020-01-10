package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEmailType;
import com.cnlive.oicc.mapper.TEmailTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Auther: 陈浩
 * @Date: 2020/1/8 16:25
 * @Description:
 */
@Service
public class TEmailTypeService {
    @Autowired
    TEmailTypeMapper tEmailTypeMapper;

    public TEmailType findTemailType(String email, String vcode, Timestamp timestamp) {
        return tEmailTypeMapper.findTemailType(email, vcode, timestamp);
    }

    public boolean update(TEmailType tEmailType) {
        return tEmailTypeMapper.updateByPrimaryKeySelective(tEmailType) > 0 ? true : false;
    }
}
