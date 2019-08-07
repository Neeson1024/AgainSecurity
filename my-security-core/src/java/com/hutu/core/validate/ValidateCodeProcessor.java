package com.hutu.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

public interface ValidateCodeProcessor {

    /**
     * 验证码放入session的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_PREFIX";


    /**
     * 创建校验码
     * @param request
     */
    void create(ServletWebRequest request);

    void validate(ServletWebRequest request);

}
