package com.admin.service.user.impl;

import com.admin.dao.user.SysUserMapper;
import com.admin.dao.user.SysUserMenuMapper;
import com.admin.model.user.SysUser;
import com.admin.model.user.SysUserMenu;
import com.admin.security.utils.PasswordUtils;
import com.admin.service.user.SysUserService;
import com.admin.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    @Override
    public List<SysUser> selectAllByPage(SysUser sysUser) {
        return sysUserMapper.selectAll(sysUser);
    }

    @Override
    public List<SysUser> selectAll(SysUser sysUser) {
        return sysUserMapper.selectAll(sysUser);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public int insertUser(SysUser sysUser) {
        // 添加用户
        sysUser.setUserId(UUIDUtils.getUUID());
        // 默认赋予系统介绍页面权限
        if (!"admin".equals(sysUser.getName())) {
            SysUserMenu sysUserMenu = new SysUserMenu();
            sysUserMenu.setUserMenuId(UUIDUtils.getUUID());
            sysUserMenu.setMenuCode("SMM0001");
            sysUserMenu.setUserName(sysUser.getName());
            sysUserMenu.setCreateTime(new Date());
            sysUserMenu.setCreateBy(sysUser.getCreateBy());
            sysUserMenuMapper.insert(sysUserMenu);
        }

        sysUser.setSalt(PasswordUtils.getSalt());  // 获取盐
        if (sysUser.getPassword() == null) {
            sysUser.setPassword("123456");
        }
        sysUser.setPassword(PasswordUtils.encode(sysUser.getPassword(), sysUser.getSalt()));  // 密码加密
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(1);
        sysUser.setDelFlag(0);
        if (sysUser.getNickName() == null || "".equals(sysUser.getNickName())) {
            sysUser.setNickName(sysUser.getName());
        }
        return sysUserMapper.addUser(sysUser);
    }

    @Override
    public int checkUserName(String userName) {
        return sysUserMapper.checkUserName(userName);
    }

    @Override
    public int checkNickName(String nickName) {
        return sysUserMapper.checkNickName(nickName);
    }

    @Override
    public List<SysUser> selectAllUserByPage(SysUser sysUser) {
        return sysUserMapper.selectAllUserByPage(sysUser);
    }

}
