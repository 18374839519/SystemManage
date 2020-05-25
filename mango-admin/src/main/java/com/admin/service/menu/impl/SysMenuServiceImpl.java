package com.admin.service.menu.impl;

import com.admin.dao.menu.SysMenuMapper;
import com.admin.dao.user.SysUserMenuMapper;
import com.admin.model.menu.SysMenu;
import com.admin.model.user.SysUserMenu;
import com.admin.security.utils.JwtTokenUtils;
import com.admin.service.menu.SysMenuService;
import com.admin.service.user.impl.SysUserMenuServiceImpl;
import com.admin.utils.datas.GlobalContents;
import com.admin.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    @Autowired
    private SysUserMenuServiceImpl sysUserMenuService;

    @Override
    public SysMenu selectByPrimaryKey(int id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public Set<String> selectPermissionsByUserName(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = sysMenuMapper.selectPermissionsByUserName(userName);
        for(SysMenu sysMenu : sysMenus) {
            if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public List<SysMenu> selectAll(Map<String, Object> map) {
        return sysMenuMapper.selectAll(map);
    }

    @Override
    public boolean insert(SysMenu record) {
        sysMenuMapper.insert(record);
        SysUserMenu sysUserMenu = new SysUserMenu();
        sysUserMenu.setUserMenuId(UUIDUtils.getUUID());
        sysUserMenu.setMenuId(record.getMenuId());
        sysUserMenu.setUserId((String) GlobalContents.superAdminMap.get("superAdmin")); // 超级管理员
        sysUserMenu.setCreateTime(new Date());
        sysUserMenu.setCreateBy(record.getCreateBy());
        return sysUserMenuMapper.insert(sysUserMenu);
    }

    @Override
    public int checkMenuName(String name, String parentId) {
        return sysMenuMapper.checkMenuName(name, parentId);
    }

    @Override
    public boolean deleteByPrimaryKey(List<String> idList) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("menuIdList", idList);
        sysUserMenuService.deleteByMenuId(paramMap);
        sysMenuMapper.deleteByPrimaryKey(paramMap);
        return true;
    }

    @Override
    public List<String> selectByParentId(Map<String, Object> map) {
        return sysMenuMapper.selectByParentId(map);
    }

    @Override
    public boolean updateById(SysMenu record) {
        return sysMenuMapper.updateById(record);
    }

    @Override
    public List<SysUserMenu> getMenusByUserId(String userId) {
        return sysUserMenuMapper.getMenusByUserId(userId);
    }

    @Override
    public List<SysMenu> selectMenusById(Map<String, Object> map) {
        return sysMenuMapper.selectMenusById(map);
    }

    @Override
    public List<SysMenu> selectCatalogIdName() {
        return sysMenuMapper.selectCatalogIdName();
    }
}
