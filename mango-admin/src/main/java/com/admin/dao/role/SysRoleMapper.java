package com.admin.dao.role;

import com.admin.model.role.SysRole;

import java.util.List;

public interface SysRoleMapper {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectAll();

    boolean updateByPrimaryKey(SysRole record);

    SysRole selectSuperAdminId();
}