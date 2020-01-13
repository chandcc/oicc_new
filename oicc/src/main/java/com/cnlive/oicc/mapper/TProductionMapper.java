package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TProduction;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author author
 */
@org.apache.ibatis.annotations.Mapper
public interface TProductionMapper extends Mapper<TProduction> {

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} order by id desc")
    List<TProduction> paginate(String type);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} and p.group_id=#{groupid} and (p.title like #{mobile} or m.mobile like #{mobile})  order by id desc")
    List<TProduction> paginate1(String type, String groupid, String mobile);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} and group_id=#{groupid}  order by id desc")
    List<TProduction> paginate_groupNotNull(String type, String groupid);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} and and (p.title like %#{mobile}% or m.mobile like %#{mobile}%) order by p.id desc")
    List<TProduction> paginate_mobileNotNull(String type, String mobile);

    @Select("select * from t_production where t_user_id = #{userId} order by create_time desc ")
    List<TProduction> getMyWorksByUidOrderBycreate_time(int userId);

}