package com.admin.dao.log;

import com.admin.model.log.SysLoginLog;

import java.util.List;

public interface SysLoginLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLoginLog record);

    SysLoginLog selectByPrimaryKey(Long id);

    List<SysLoginLog> selectAll();

    int updateByPrimaryKey(SysLoginLog record);
}