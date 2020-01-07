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
public class TEmailType implements Serializable {

    private static final long serialVersionUID = 1578365472229L;


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
    private String nickname;

    /**
    * 
    * isNullAble:1
    */
    private Timestamp insertDate;



}
