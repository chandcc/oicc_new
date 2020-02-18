package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 项目名：oicc
 * 包名：com.cnlive.oicc.mapper
 * Author by 陈庆彪, Date on 2020/2/18,10:44.
 * PS: Not easy to write code, please indicate.
 */
@org.apache.ibatis.annotations.Mapper
public interface TRoleMapper {
    @Select("select * from t_role")
    List<TRole> findRolesList();
}
