package com.admin.controller.menu;

import com.admin.model.menu.SysMenu;
import com.admin.model.user.SysUserMenu;
import com.admin.security.utils.JwtTokenUtils;
import com.admin.service.menu.impl.SysMenuServiceImpl;
import com.admin.utils.datas.MenuData;
import com.admin.utils.datas.MenuDataUtils;
import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.admin.utils.page.PageRequest;
import com.admin.utils.uuid.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @GetMapping("/getAllMenu")
    public HttpResult getAllMenu(PageRequest pageRequest, SysMenu sysMenu) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", sysMenu.getName().trim());
        List<SysMenu> list = sysMenuService.selectAll(paramsMap);
        PageInfo<SysMenu> pageInfo = new PageInfo<>(list);
        return HttpResultUtils.success(pageInfo);
    }

    @GetMapping("/getMenusByUserId")
    public HttpResult getMenusByUserId(String userId) {
        List<SysUserMenu> menuList = sysMenuService.getMenusByUserId(userId);
        if (menuList.size() == 0) {
            return HttpResultUtils.success();
        }
        List<String> menuIdListStr = new ArrayList<>();
        for (SysUserMenu sysUserMenu : menuList) {
            menuIdListStr.add(sysUserMenu.getMenuId());
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("menuIdListStr", menuIdListStr);
        List<SysMenu> menuLists = sysMenuService.selectMenusById(paramMap);
        List<MenuData> menuDataList = new ArrayList<>();
        for (int i=0; i<menuLists.size(); i++) {
            MenuData menuData = new MenuData();
            menuData.setId(menuLists.get(i).getMenuId());
            menuData.setName(menuLists.get(i).getName());
            menuData.setUrl(menuLists.get(i).getUrl());
            menuData.setIcon(menuLists.get(i).getIcon());
            menuData.setUpId(menuLists.get(i).getParentId());
            menuData.setData(menuLists.get(i));
            menuDataList.add(menuData);
        }
        MenuDataUtils menuDataUtils = new MenuDataUtils(menuDataList);
        List<MenuData> returnList = menuDataUtils.builTree();
        return HttpResultUtils.success(returnList);
    }

    @PostMapping("/insertMenu")
    public HttpResult insertMenu(HttpServletRequest request, SysMenu sysMenu) {
        sysMenu.setMenuId(UUIDUtils.getUUID());
        sysMenu.setCreateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysMenu.setCreateTime(new Date());
        sysMenu.setDelFlag(0);
        boolean result = sysMenuService.insert(sysMenu);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "添加失败");
        }
        return HttpResultUtils.success();
    }

    @GetMapping("/checkMenuName")
    public HttpResult checkMenuName(String name, int parentId) {
        int result = sysMenuService.checkMenuName(name, parentId);
        if (result > 0) {
            return HttpResultUtils.success(false);
        }
        return HttpResultUtils.success(true);
    }

    @PostMapping("/deleteMenu")
    public HttpResult deleteMenu(Long id) {
        List<Integer> list = new ArrayList<>();
        list.add(id.intValue());
        List<Integer> parentIdList = selectByParentId(list);
        parentIdList.addAll(list);
        boolean result = sysMenuService.deleteByPrimaryKey(parentIdList);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "删除失败");
        }
        return HttpResultUtils.success();
    }

    /**
     * 递归获取所有id
     * @param parentIdList
     * @return
     */
    private List<Integer> selectByParentId(List<Integer> parentIdList) {
        List<Integer> returnList = new ArrayList<>();
        List<Integer> list = sysMenuService.selectByParentId(parentIdList);
        if (list.size() > 0) {
            returnList.addAll(list);
            selectByParentId(list);
        }
        return returnList;
    }

    @PostMapping("/updateById")
    public HttpResult updateById(HttpServletRequest request, SysMenu sysMenu) {
        sysMenu.setLastUpdateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysMenu.setLastUpdateTime(new Date());
        boolean result = sysMenuService.updateById(sysMenu);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "更新失败");
        }
        return HttpResultUtils.success();
    }

    @GetMapping("/selectById")
    public HttpResult selectById(int id) {
        SysMenu sysMenu = sysMenuService.selectByPrimaryKey(id);
        return HttpResultUtils.success(sysMenu);
    }

    @GetMapping("/selectCatalogIdName")
    public HttpResult selectCatalogIdName() {
        List<SysMenu> list = sysMenuService.selectCatalogIdName();
        return HttpResultUtils.success(list);
    }

}
