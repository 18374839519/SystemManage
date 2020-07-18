package com.admin.service.menu;

import com.admin.model.menu.SysMenu;
import com.admin.model.user.SysUserMenu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService {

    SysMenu selectByPrimaryKey(int id);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> selectPermissionsByUserName(String userName);

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenu> selectAll(Map<String, Object> map);

    /**
     * 添加
     * @param record
     * @return
     */
    boolean insert(SysMenu record);

    /**
     * 校验名称是否存在
     * @param name
     * @param parentId
     * @return
     */
    int checkMenuName(String name, String parentId);

    /**
     * 删除
     * @param idList
     * @return
     */
    boolean deleteByPrimaryKey(List<String> idList);

    /**
     * 根据父节点查询
     * @param map
     * @return
     */
    List<String> selectByParentId(Map<String, Object> map);

    /**
     * 根据id修改菜单/目录
     * @param record
     * @return
     */
    boolean updateByCode(SysMenu record);

    List<SysUserMenu> getMenusByuserName(String userName);

    List<SysMenu> selectMenusByMenuCode(Map<String, Object> map);

    List<SysMenu> selectCatalogIdName();

    String selectSystemIntroduce();
}
