package com.admin.config.initdata;

import com.admin.model.role.SysRole;
import com.admin.model.user.SysUserRole;
import com.admin.service.role.impl.SysRoleServiceImpl;
import com.admin.service.user.impl.SysUserRoleServiceImpl;
import com.admin.utils.datas.GlobalContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

// 项目启动时进行实例化
@Component
public class InitData {

    private static Logger logger = LoggerFactory.getLogger(InitData.class);

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysUserRoleServiceImpl sysUserRoleService;

    // 实例化超级管理员id
    @PostConstruct
    public void initSuperAdmin() {
        logger.info("实例化超级管理员id开始...");
        SysRole sysRole = sysRoleService.selectSuperAdminId();
        SysUserRole sysUserRole = sysUserRoleService.selectUserIdByRoleId(sysRole.getRoleId());
        GlobalContents.superAdminMap.put("superAdmin", sysUserRole.getUserId());
        logger.info("实例化超级管理员id结束...");
    }
}
