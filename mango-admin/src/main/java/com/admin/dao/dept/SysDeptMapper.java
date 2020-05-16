package com.admin.dao.dept;

import com.admin.model.dept.SysDept;

import java.util.List;

public interface SysDeptMapper {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(SysDept record);

    SysDept selectByPrimaryKey(Long id);

    List<SysDept> selectAll();

    boolean updateByPrimaryKey(SysDept record);
}