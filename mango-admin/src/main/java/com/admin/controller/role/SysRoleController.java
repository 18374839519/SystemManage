package com.admin.controller.role;

import com.admin.model.role.SysRole;
import com.admin.security.utils.JwtTokenUtils;
import com.admin.service.role.impl.SysRoleServiceImpl;
import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.admin.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @PostMapping("/insertRole")
    public HttpResult insertRole(HttpServletRequest request, SysRole sysRole) {
        sysRole.setCreateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysRole.setCreateTime(new Date());
        sysRole.setRoleId(UUIDUtils.getUUID());
        sysRole.setDelFlag(0);
        boolean result = sysRoleService.insertSysRole(sysRole);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "角色添加失败");
        }
        return HttpResultUtils.success();
    }
}
