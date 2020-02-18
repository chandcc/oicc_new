package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TPermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 项目名：dream-security
 * 包名：com.cnlive.oicc.mapper
 * Author by 陈庆彪, Date on 2020/2/17,11:18.
 * PS: Not easy to write code, please indicate.
 */
public interface TPermissionMapper {
    @Select("select * from t_permission where id=#{rolecode}")
    List<TPermission> getPermissionsByRoleCode(int rolecode);

}
