package com.admin.utils.imports.service;

import com.admin.model.menu.SysMenu;
import com.admin.model.user.SysUser;
import com.admin.utils.uuid.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImportServiceImpl {

    public boolean importUsers(List<List<List<Object>>> lists) {
        for (int i=0; i<lists.size(); i++) {
            List<List<Object>> listRow = lists.get(i);
            for (int j=0; j<listRow.size(); j++) {
                List<Object> listCol = listRow.get(j);
                for (int k=0; k<listCol.size(); k++) {
                    SysUser user = new SysUser();
                    user.setUserId(UUIDUtils.getUUID());
                }
            }
        }
        return true;
    }

    public boolean importMenus(List<List<List<Object>>> lists) {
        for (int i=0; i<lists.size(); i++) {
            List<List<Object>> listRow = lists.get(i);
            for (int j=0; j<listRow.size(); j++) {
                List<Object> listCol = listRow.get(j);
                SysMenu menu = new SysMenu();
                menu.setMenuId(UUIDUtils.getUUID());
                menu.setMenuCode((String) listCol.get(0));
                menu.setName((String) listCol.get(1));
                menu.setParentId((String) listCol.get(2));
            }
        }
        return true;
    }
}
