package com.admin.dao.user;

import com.admin.model.user.SysUserMenu;

import java.util.List;

public interface SysUserMenuMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(SysUserMenu record);

    SysUserMenu selectByPrimaryKey(Integer id);

    List<SysUserMenu> selectAll();

    boolean updateByPrimaryKey(SysUserMenu record);

    List<SysUserMenu> getMenusByUserId(String userId);
}