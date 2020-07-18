package com.admin.config.initdata;

import com.admin.model.role.SysRole;
import com.admin.model.user.SysUserRole;
import com.admin.service.menu.impl.SysMenuServiceImpl;
import com.admin.service.role.impl.SysRoleServiceImpl;
import com.admin.service.user.impl.SysUserRoleServiceImpl;
import com.admin.utils.datas.GlobalContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 项目启动时进行实例化
@Component
public class InitData {

    private static Logger logger = LoggerFactory.getLogger(InitData.class);

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private SysUserRoleServiceImpl sysUserRoleService;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    // 系统实例化
    @PostConstruct
    public void initMethod() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        logger.info("系统实例化开始...");
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                initSuperAdmin();
                //initSystemIntroducePage();
            }
        });
        logger.info("系统实例化结束...");
    }

    // 实例化超级管理员id
    private void initSuperAdmin() {
        logger.info("实例化超级管理员id开始...");
        SysUserRole sysUserRole = sysUserRoleService.selectuserNameByrole("superAdmin");
        GlobalContents.superAdminMap.put("superAdmin", sysUserRole.getUserName());
        logger.info("实例化超级管理员id结束...");
    }

    // 实例化系统介绍页面
    private void initSystemIntroducePage() {
        logger.info("实例化系统介绍页面开始...");
        String menuId = sysMenuService.selectSystemIntroduce();
        GlobalContents.systemIntroduceMenuId.put("menuId", menuId);
        logger.info("实例化系统介绍页面结束...");
    }
}
