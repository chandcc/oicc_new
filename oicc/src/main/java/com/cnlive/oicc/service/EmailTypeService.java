package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TEmailType;
import com.cnlive.oicc.mapper.TEmailTypeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @Auther: 陈浩
 * @Date: 2020/1/8 16:25
 * @Description:
 */
@AllArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class EmailTypeService {
  private final TEmailTypeMapper tEmailTypeMapper;

    public TEmailType findTemailType(String email, String vcode, Timestamp timestamp) {
        return tEmailTypeMapper.findTemailType(email, vcode, timestamp);
    }

    public boolean update(TEmailType tEmailType) {
        return tEmailTypeMapper.updateByPrimaryKeySelective(tEmailType) > 0 ? true : false;
    }
}
