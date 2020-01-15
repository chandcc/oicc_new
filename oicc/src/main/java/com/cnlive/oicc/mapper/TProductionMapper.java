package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TProduction;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author author
 */
@org.apache.ibatis.annotations.Mapper
public interface TProductionMapper extends Mapper<TProduction> {

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} order by id desc")
    List<Map<String,Object>> paginate(String type);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} and p.group_id=#{groupid} and (p.title like CONCAT('%',#{mobile},'%')  or m.mobile like CONCAT('%',#{mobile},'%') )  order by id desc")
    List<Map<String,Object>> paginate1(@Param("type")String type, @Param("groupid")String groupid,@Param("mobile") String mobile);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type} and group_id=#{groupid}  order by id desc")
    List<Map<String,Object>> paginate_groupNotNull(@Param("type") String type, @Param("groupid") String groupid);

    @Select("select p.*,m.mobile from t_production p,t_member m where m.id=p.t_user_id and is_upload=1 and type=#{type}  and (p.title like CONCAT('%',#{mobile},'%') or m.mobile like CONCAT('%',#{mobile},'%') ) order by p.id desc")
    List<Map<String,Object>> paginate_mobileNotNull(@Param("type")String type,@Param("mobile") String mobile);

    @Select("select * from t_production where t_user_id = #{userId} order by create_time desc ")
    List<TProduction> getMyWorksByUidOrderBycreate_time(int userId);

}