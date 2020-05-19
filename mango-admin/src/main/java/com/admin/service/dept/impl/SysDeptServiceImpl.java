package com.admin.service.dept.impl;

import com.admin.dao.dept.SysDeptMapper;
import com.admin.model.dept.SysDept;
import com.admin.service.dept.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public boolean insertDept(SysDept sysDept) {
        return sysDeptMapper.insert(sysDept);
    }
}
