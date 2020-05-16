package com.admin.service.role.impl;

import com.admin.dao.role.SysRoleMapper;
import com.admin.model.role.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public boolean insertSysRole(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }
}
