package com.cnlive.oicc.bean;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
*
*  @author author
*/
@Data
public class TMember implements Serializable {

    private static final long serialVersionUID = 1578365476877L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    private Integer id;

    /**
    * 
    * isNullAble:1
    */
    private String encryptID;

    /**
    * 用户昵称
    * isNullAble:1
    */
    private String nickName;

    /**
    * 手机号
    * isNullAble:0
    */
    private String mobile;

    /**
    * 会员级别： 0：普通会员  1：高级会员
    * isNullAble:0,defaultVal:0
    */
    private Integer level;

    /**
    * 用户凭证
    * isNullAble:0
    */
    private String token;

    /**
    * 
    * isNullAble:1
    */
    private String ext;

    /**
    * 是否报名文创北斗：0，没有；1，有
    * isNullAble:1,defaultVal:0
    */
    private Integer wcbd;

    /**
    * 是否报名原创ip：0，否；1，有
    * isNullAble:1,defaultVal:0
    */
    private Integer ycip;

    /**
    * 是否报名角色创意：0，否；1是
    * isNullAble:1,defaultVal:0
    */
    private Integer jscy;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDateOicc;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp createDateOicc;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDateIp;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp createDateIp;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDateJs;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp createDateJs;



}
