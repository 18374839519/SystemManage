package com.admin.service.menu.impl;

import com.admin.dao.menu.SysMenuMapper;
import com.admin.model.menu.SysMenu;
import com.admin.service.menu.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

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
    public List<SysMenu> selectAll() {
        return sysMenuMapper.selectAll();
    }

    @Override
    public boolean insert(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public int checkMenuName(String name, int parentId) {
        return sysMenuMapper.checkMenuName(name, parentId);
    }

    @Override
    public boolean deleteByPrimaryKey(List<Integer> idList) {
        return sysMenuMapper.deleteByPrimaryKey(idList);
    }

    @Override
    public List<Integer> selectByParentId(List<Integer> parentIdList) {
        return sysMenuMapper.selectByParentId(parentIdList);
    }

    @Override
    public boolean updateById(SysMenu record) {
        return sysMenuMapper.updateById(record);
    }
}
