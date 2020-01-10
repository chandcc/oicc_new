package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TEmailType;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.sql.Timestamp;

/**
*  @author author
*/
@org.apache.ibatis.annotations.Mapper
public interface TEmailTypeMapper extends Mapper<TEmailType> {
    @Select("select * from t_email_type where email=#{email} and vcode=#{vcode} and insertDate>#{timestamp}")
     TEmailType findTemailType(String email, String vcode, Timestamp timestamp);

}