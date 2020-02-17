package com.cnlive.oicc.entity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
*
*  @author author
*/
@Data
@Table(name = "t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1578365480865L;


    /**
    * 
    * isNullAble:0
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * 登陆账号(唯一登陆标识)
    * isNullAble:1
    */
    private String name;

    /**
    * 所属用户组id
    * isNullAble:1
    */
    private String password;

    /**
    * 性质
    * isNullAble:1
    */
    private String property;

    /**
    * 建立时间
    * isNullAble:1,defaultVal:CURRENT_TIMESTAMP
    */
    private Timestamp createTime;

    /**
    * 添加成员的操作用户id （所属代理商）
    * isNullAble:1
    */
    private Integer managerId;

    /**
    * 角色：admin管理员;user报名选手；manager市区代理商；managers省代理商 judge评委
    * isNullAble:0
    */
    private String role;

    /**
    * 联系地址
    * isNullAble:1
    */
    private String address;

    /**
    * 联系人
    * isNullAble:1
    */
    private String contact;

    /**
    * 联系电话
    * isNullAble:1
    */
    private String tel;
    /**
     * 角色列表
     */
    private List<TRole> roles;



}
