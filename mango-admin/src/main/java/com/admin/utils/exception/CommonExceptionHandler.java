package com.admin.utils.exception;

import com.admin.utils.http.HttpResult;
import com.admin.utils.http.HttpResultUtils;
import com.admin.utils.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常工具类
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    /**
     * java一般异常
     */
    @ExceptionHandler(Exception.class)
    public HttpResult exceptionHandler(Exception e) {
        //e.printStackTrace(); // 异常信息打印到控制台
        logger.info(e.getMessage());
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return HttpResultUtils.error(baseException.getCode(), baseException.getMessage());
        }

        return HttpResultUtils.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "错误");
    }
}
