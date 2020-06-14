package com.admin.service.dept;

import com.admin.model.dept.SysDept;

import java.util.List;

public interface SysDeptService {

    boolean insertDept(SysDept sysDept);

    List<SysDept> selectAll();
}
