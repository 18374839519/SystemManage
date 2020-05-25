package com.admin.service.user;

import com.admin.model.user.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysUserRoleService {

    SysUserRole selectUserIdByRoleId(String roleId);

}
