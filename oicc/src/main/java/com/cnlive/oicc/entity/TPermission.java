package com.cnlive.oicc.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * 项目名：dream-security
 * 包名：com.cnlive.oicc.entity
 * Author by 陈庆彪, Date on 2020/2/17,10:15.
 * PS: Not easy to write code, please indicate.
 * 请求资源记录表
 */
@Data
@Table(name = "t_permission")
public class TPermission {
    private int id;
    private String permissionName;
    private String permissionCode;
    private String type;
    private String url;
    private int pid;

}
