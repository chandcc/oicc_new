package com.cnlive.oicc.entity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
*
*  @author author
*/
@Data
@Table(name = "t_canhui")
public class TCanhui implements Serializable {
    private static final long serialVersionUID = 1578365467380L;
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
