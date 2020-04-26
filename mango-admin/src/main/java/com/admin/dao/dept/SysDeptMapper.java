package com.admin.dao.dept;

import com.admin.model.dept.SysDept;

import java.util.List;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    List<SysDept> selectAll();

    int updateByPrimaryKey(SysDept record);
}