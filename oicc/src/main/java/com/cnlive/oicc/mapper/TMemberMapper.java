package com.cnlive.oicc.mapper;

import com.cnlive.oicc.entity.TMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author author
 */
public interface TMemberMapper extends Mapper<TMember> {

    @Select("select * from  t_member where  #{tableName} =1 and  id not in (select memberId from t_enrol)")
    public List<TMember> findAllidNotInEnrol(@Param("tableName") String tableName);

    @Select("select * from  t_member where ${tableName} =1 and  id not in (select t_user_id from t_production where type= #{type} )")
    public List<TMember> findAllidNotInproduction(@Param("tableName") String tableName, @Param("type") String type);

    @Select({"<script>select *  from t_member where ${tableName} =1 <when test='mobile!=null'>and  (mobile like CONCAT('%',#{mobile},'%') " +
            "or nickName like CONCAT('%',#{mobile},'%'))</when> order by ${time}  desc </script>"})
    List<Map<String, Object>> paginate(@Param("tableName") String tableName, @Param("time") String time, @Param("mobile") String mobile);

    @Select({"<script>select *  from t_member  where ${tableName}  =1 and  id  in (select memberId from t_enrol)  <when test='mobile!=null'>" +
            "and (mobile like CONCAT('%',#{mobile},'%') " +
            "or nickName like CONCAT('%',#{mobile},'%') ) </when> order by ${time}  desc</script>"})
    List<Map<String, Object>> paginate1(@Param("tableName") String tableName, @Param("time") String time, @Param("mobile") String mobile);

    @Select({"<script>select * from t_member  where tableName  =1 and  id not in (select memberId from t_enrol) <when test='mobile!=null'>" +
            "and (mobile like CONCAT('%',#{mobile},'%')" +
            " or nickName like CONCAT('%',#{mobile},'%') ) </when> order by ${time} desc </script>"})
    List<Map<String, Object>> paginate2(@Param("tableName") String tableName, @Param("time") String time, @Param("mobile") String mobile);

    @Select({"<script>select * from t_member  where ${tableName}  =1 and  id not in (select t_user_id from t_production where type= #{type} )  " +
            "<when test='mobile!=null'>and (mobile like CONCAT('%',#{mobile},'%') or nickName like CONCAT('%',#{mobile},'%') ) </when>" +
            "order by ${time}  desc</script>"})
    List<Map<String, Object>> paginate3(@Param("tableName") String tableName, @Param("time") String time, @Param("mobile") String mobile,@Param("type")String type);

    @Select({"<script>select t.*  from t_member t,t_production p where t.${tableName} =1 and p.type=#{type}  and t.id=p.t_user_id <when test='mobile!=null'>and  p.is_upload=#{upload}" +
            " </when><when test='mobile!=null'> and (t.mobile like CONCAT('%',#{mobile},'%') or t.nickName like CONCAT('%',#{mobile},'%') ) </when >order by ${time}  desc</script>"})
    List<Map<String, Object>> paginate4(@Param("tableName") String tableName, @Param("time") String time, @Param("mobile") String mobile, @Param("upload") int upload,@Param("type")String type);
}