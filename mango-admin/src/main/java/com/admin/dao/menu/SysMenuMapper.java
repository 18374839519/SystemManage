package com.admin.dao.menu;

import com.admin.model.menu.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    boolean deleteByPrimaryKey(Map<String, Object> map);

    boolean insert(SysMenu record);

    SysMenu selectByPrimaryKey(int id);

    List<SysMenu> selectAll(Map<String, Object> map);

    boolean updateByPrimaryKey(SysMenu record);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    List<SysMenu> selectPermissionsByUserName(String userName);

    /**
     * 校验名称是否存在
     * @param name
     * @param parentId
     * @return
     */
    int checkMenuName(String name, String parentId);

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
    boolean updateById(SysMenu record);

    List<SysMenu> selectMenusById(Map<String, Object> map);

    List<SysMenu> selectCatalogIdName();
}