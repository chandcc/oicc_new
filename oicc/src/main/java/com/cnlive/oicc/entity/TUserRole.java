package com.cnlive.oicc.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * 项目名：dream-security
 * 包名：com.cnlive.oicc.entity
 * Author by 陈庆彪, Date on 2020/2/17,10:16.
 * PS: Not easy to write code, please indicate.
 * 用户角色中间表
 */
@Data
@Table(name = "t_user_role")
public class TUserRole {
    private int id;
    private int useId;
    private int roleId;
}
