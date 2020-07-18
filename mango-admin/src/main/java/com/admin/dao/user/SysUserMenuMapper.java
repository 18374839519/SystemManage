package com.admin.dao.user;

import com.admin.model.user.SysUserMenu;

import java.util.List;
import java.util.Map;

public interface SysUserMenuMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(SysUserMenu record);

    SysUserMenu selectByPrimaryKey(Integer id);

    List<SysUserMenu> selectAll();

    boolean updateByPrimaryKey(SysUserMenu record);

    List<SysUserMenu> getMenusByuserName(String userName);

    boolean deleteByMenuId(Map<String, Object> map);
}