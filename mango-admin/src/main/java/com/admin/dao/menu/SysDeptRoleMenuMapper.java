package com.admin.dao.menu;

import com.admin.model.menu.SysDeptRoleMenu;

import java.util.List;

public interface SysDeptRoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDeptRoleMenu record);

    SysDeptRoleMenu selectByPrimaryKey(Integer id);

    List<SysDeptRoleMenu> selectAll();

    int updateByPrimaryKey(SysDeptRoleMenu record);
}