package com.cnlive.oicc.mapper;


import com.cnlive.oicc.entity.TCanhui;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
*  @author author
*/
@org.apache.ibatis.annotations.Mapper
public interface TCanhuiMapper extends Mapper<TCanhui> {
    @Select("select *  from t_canhui order by id desc")
    public List<TCanhui> paginate();
    @Select("select * from t_canhui where mobile like #{query} or userName like #{query} order by id desc")
    public List<TCanhui> paginate1(String query);


}