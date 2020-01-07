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
public class TProduction implements Serializable {

    private static final long serialVersionUID = 1578365478905L;


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
