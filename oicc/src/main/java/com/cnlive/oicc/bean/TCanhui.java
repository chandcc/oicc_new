package com.cnlive.oicc.bean;
import lombok.Data;

import java.io.Serializable;
/**
*
*  @author author
*/
@Data
public class TCanhui implements Serializable {

    private static final long serialVersionUID = 1578365467380L;


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
    private String userName;

    /**
    * 
    * isNullAble:1
    */
    private String mobile;

    /**
    * 
    * isNullAble:1
    */
    private String email;

}
