package com.cnlive.oicc.service.impl;

import com.cnlive.oicc.entity.TRole;
import com.cnlive.oicc.mapper.TRoleMapper;
import com.cnlive.oicc.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目名：oicc
 * 包名：com.cnlive.oicc.service.impl
 * Author by 陈庆彪, Date on 2020/2/18,10:42.
 * PS: Not easy to write code, please indicate.
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
private final TRoleMapper tRoleMapper;

    @Override
    public List<TRole> findRolesList() {
        return tRoleMapper.findRolesList();
    }
}
