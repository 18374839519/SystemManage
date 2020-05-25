package com.admin.service.user.impl;

import com.admin.dao.user.SysUserRoleMapper;
import com.admin.model.user.SysUserRole;
import com.admin.service.user.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public SysUserRole selectUserIdByRoleId(String roleId) {
        return sysUserRoleMapper.selectUserIdByRoleId(roleId);
    }
}
