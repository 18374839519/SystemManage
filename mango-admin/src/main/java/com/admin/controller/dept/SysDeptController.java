package com.admin.controller.dept;

import com.admin.model.dept.SysDept;
import com.admin.security.utils.JwtTokenUtils;
import com.admin.service.dept.impl.SysDeptServiceImpl;
import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.admin.utils.uuid.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private SysDeptServiceImpl sysDeptService;

    @PostMapping("insertDept")
    public HttpResult insertDept(HttpServletRequest request, SysDept sysDept) {
        sysDept.setDeptId(UUIDUtils.getUUID());
        sysDept.setCreateBy(JwtTokenUtils.getUsernameFromToken(JwtTokenUtils.getToken(request)));
        sysDept.setCreateTime(new Date());
        sysDept.setDelFlag(0);
        boolean result = sysDeptService.insertDept(sysDept);
        if (!result) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "部门添加失败");
        }
        return HttpResultUtils.success();
    }

    @GetMapping("/getAllDept")
    public HttpResult getAllDept() {
        List<SysDept> list = sysDeptService.selectAll();
        return HttpResultUtils.success(list);
    }
}
