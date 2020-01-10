package com.cnlive.oicc.entity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
*
*  @author author
*/
@Data
@Table(name = "t_enrol")
public class TEnrol implements Serializable {

    private static final long serialVersionUID = 1578365474547L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
