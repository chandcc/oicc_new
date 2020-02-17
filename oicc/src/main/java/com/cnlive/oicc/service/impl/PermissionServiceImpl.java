package com.cnlive.oicc.service.impl;

import com.cnlive.oicc.entity.TPermission;
import com.cnlive.oicc.mapper.TPermissionMapper;
import com.cnlive.oicc.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private TPermissionMapper permissionMapper;


    @Override
    public List<TPermission> getPermissionsByRoleCode(String rolecode) {
        return permissionMapper.getPermissionsByRoleCode(rolecode);
    }
}
