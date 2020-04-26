package com.admin.dao.dict;

import com.admin.model.dict.SysDict;

import java.util.List;

public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    List<SysDict> selectAll();

    int updateByPrimaryKey(SysDict record);
}