package com.cnlive.oicc.bean;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
*
*  @author author
*/
@Data
public class TEmailPwd implements Serializable {

    private static final long serialVersionUID = 1578365469645L;


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
