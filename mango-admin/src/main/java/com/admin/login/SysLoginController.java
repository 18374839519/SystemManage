package com.admin.login;

import com.admin.model.user.SysUser;
import com.admin.security.utils.PasswordUtils;
import com.admin.security.utils.SecurityUtils;
import com.admin.security.utils.model.JwtAuthenticatioToken;
import com.admin.service.user.impl.SysUserServiceImpl;
import com.admin.utils.captcha.VerifyCode;
import com.admin.utils.exception.BaseException;
import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关API
 */
@RestController
public class SysLoginController {

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证码
     */
    @GetMapping("/captcha.jpg")
    public HttpResult captcha() {

        try {
            VerifyCode verifyCode = new VerifyCode();
            verifyCode.output(verifyCode.getImage(), new FileOutputStream("G:/SysWeb/sysManageWeb/src/assets/kaptcha.jpg"));
            String text = verifyCode.getText();
            return HttpResultUtils.success(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpResultUtils.success();
    }

    /**
     * 登录接口
     * @return
     */
    @PostMapping("/login")
    public HttpResult login(LoginBean loginBean, HttpServletRequest request) {
        String userName = loginBean.getAccount();
        String password = loginBean.getPassword();

        // 用户信息
        SysUser sysUser = sysUserServiceImpl.selectByUserName(userName);

        // 账号或密码错误
        if (sysUser == null || (!PasswordUtils.matches(sysUser.getSalt(), password, sysUser.getPassword()))) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "账号或密码错误");
        }

        // 账号锁定
        if (sysUser.getStatus() == 0) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "账号被锁定");
        }

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, userName, password, authenticationManager);
        if (token == null) {
            throw new BaseException(HttpStatus.ERROR_SERVICE_VALIDATOR, "登录失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", sysUser);
        return HttpResultUtils.success(map);
    }
}
