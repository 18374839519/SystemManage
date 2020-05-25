package com.admin.dao.user;

import com.admin.model.user.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long id);

    List<SysUserRole> selectAll();

    int updateByPrimaryKey(SysUserRole record);

    SysUserRole selectUserIdByRoleId(String roleId);
}