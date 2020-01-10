package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TEmailPwd;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.sql.Timestamp;

/**
 * @author author
 */
@org.apache.ibatis.annotations.Mapper
public interface TEmailPwdMapper extends Mapper<TEmailPwd> {
    @Select("select * from t_email_pwd where email=#{email} and vcode=#{vcode} and insertDate>#{timestamp}")
    TEmailPwd findTemailType(String email, String vcode, Timestamp timestamp);

}