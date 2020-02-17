package com.cnlive.oicc.service;

import com.cnlive.oicc.entity.TPermission;

import java.util.List;

public interface PermissionService {
    List<TPermission> getPermissionsByRoleCode(String rolecode);
}
