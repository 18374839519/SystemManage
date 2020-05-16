package com.admin.dao.user;

import com.admin.model.user.SysUserDept;

import java.util.List;

public interface SysUserDeptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserDept record);

    SysUserDept selectByPrimaryKey(Integer id);

    List<SysUserDept> selectAll();

    int updateByPrimaryKey(SysUserDept record);
}