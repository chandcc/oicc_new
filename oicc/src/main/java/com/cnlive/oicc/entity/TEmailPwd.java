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
@Table(name = "t_email_pwd")
public class TEmailPwd implements Serializable {

    private static final long serialVersionUID = 1578365469645L;


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
    private String email;

    /**
    * 
    * isNullAble:1
    */
    private String vcode;

    /**
    * 
    * isNullAble:1
    */
    private Integer type;

    /**
    * 
    * isNullAble:1
    */
    private String userPwd;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDate;


}
