package com.cnlive.oicc.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * 项目名：dream-security
 * 包名：com.cnlive.oicc.entity
 * Author by 陈庆彪, Date on 2020/2/17,10:15.
 * PS: Not easy to write code, please indicate.
 * 角色表
 */
@Table(name = "t_role")
@Data
public class TRole {
    private int id;
    private String roleName;
    private String roleCode;
}
