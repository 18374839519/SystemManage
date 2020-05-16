package com.admin.service.user;

import com.admin.model.user.SysUser;

import java.util.List;

public interface SysUserService {

    /**
     * 查询所有
     */
    List<SysUser> selectAllByPage(SysUser sysUser);

    List<SysUser> selectAll(SysUser sysUser);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    SysUser selectByUserName(String userName);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    int insertUser(SysUser sysUser);

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    int checkUserName(String userName);

    /**
     * 检查昵称是否存在
     * @param nickName
     * @return
     */
    int checkNickName(String nickName);

}
