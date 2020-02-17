package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TUser;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
*  @author author
*/
@org.apache.ibatis.annotations.Mapper
public interface TUserMapper extends Mapper<TUser> {
    @Select("select * from t_user where role = #{role} order by create_time desc")
     List<TUser> paginate1(String role);
    @Select("select * from t_user order by create_time desc")
     List<TUser> paginate();

    @Select("select * from t_user where name=#{username}")
    TUser getUserByName(String username);


}