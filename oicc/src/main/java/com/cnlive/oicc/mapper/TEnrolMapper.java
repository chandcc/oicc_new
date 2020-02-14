package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TEnrol;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
*  @author author
*/
@org.apache.ibatis.annotations.Mapper
public interface TEnrolMapper extends Mapper<TEnrol> {
    @Select("select * from t_enrol order by insert_date desc")
    List<TEnrol> getEnrolOrderbyinsertDate();
    @Select("select * from t_enrol where member_id like #{filde} or tel like #{filde} order by insert_date desc")
    List<TEnrol> getEnrolOrderbyinsertDate1(String filde);


}