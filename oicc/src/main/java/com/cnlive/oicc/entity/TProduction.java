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
@Table(name = "t_production")
public class TProduction implements Serializable {

    private static final long serialVersionUID = 1578365478905L;


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
    private Integer tUserId;

    /**
    * 
    * isNullAble:1
    */
    private String title;

    /**
    * /opt/asdt.mpg
    * isNullAble:1
    */
    private String filePath;

    /**
    * 
    * isNullAble:1,defaultVal:CURRENT_TIMESTAMP
    */
    private Timestamp insertTime;

    /**
    * 
    * isNullAble:1
    */
    private String domain;

    /**
    * 0:没上传金山 1:已上传
    * isNullAble:1,defaultVal:0
    */
    private Integer isUpload;

    /**
    * 
    * isNullAble:1
    */
    private Integer groupId;

    /**
    * 报名方式：0，文创北斗；1，原创IP
    * isNullAble:1
    */
    private Timestamp createTime;

    /**
    * 报名方式：0，文创北斗；1，原创IP
    * isNullAble:1
    */
    private Integer type;



}
