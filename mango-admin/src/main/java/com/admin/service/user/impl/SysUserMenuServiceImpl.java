package com.admin.service.user.impl;

import com.admin.dao.user.SysUserMenuMapper;
import com.admin.service.user.SysUserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserMenuServiceImpl implements SysUserMenuService {

    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;

    @Override
    public boolean deleteByMenuId(Map<String, Object> map) {
        return sysUserMenuMapper.deleteByMenuId(map);
    }
}
