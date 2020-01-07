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
public class TEnrol implements Serializable {

    private static final long serialVersionUID = 1578365474547L;


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
    private Integer memberId;

    /**
    * 
    * isNullAble:1
    */
    private String username;

    /**
    * 
    * isNullAble:1
    */
    private Integer age;

    /**
    * 
    * isNullAble:1
    */
    private String company;

    /**
    * 
    * isNullAble:1
    */
    private Integer sex;

    /**
    * 
    * isNullAble:1
    */
    private String tel;

    /**
    * 
    * isNullAble:1
    */
    private String IdNumber;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDate;

    /**
    * 
    * isNullAble:1
    */
    private String companyTel;

    /**
    * 
    * isNullAble:1
    */
    private String companyLinkman;

    /**
    * 是否团队，0为个人报名，1为团体报名
    * isNullAble:1,defaultVal:0
    */
    private Integer isTeam;



}
