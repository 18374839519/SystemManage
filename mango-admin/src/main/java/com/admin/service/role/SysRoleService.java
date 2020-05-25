package com.admin.service.role;

import com.admin.model.role.SysRole;

import java.util.List;

public interface SysRoleService {

    boolean insertSysRole(SysRole sysRole);

    SysRole selectSuperAdminId();

}
