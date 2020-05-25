package com.admin.service.role.impl;

import com.admin.dao.role.SysRoleMapper;
import com.admin.model.role.SysRole;
import com.admin.service.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public boolean insertSysRole(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    @Override
    public SysRole selectSuperAdminId() {
        return sysRoleMapper.selectSuperAdminId();
    }
}
