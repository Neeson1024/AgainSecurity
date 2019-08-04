package com.hutu.core.validate.impl;

import com.hutu.core.validate.ValidateCodeGenerator;
import com.hutu.core.validate.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
public abstract class AbstractValidateCodeProcessor<T> implements ValidateCodeProcessor {

    /**
     * 操作session 工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 收集系统中所有ValidateCodeGenerator的接口实现
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGeneratorMap;


    public void create(ServletWebRequest request){
        T validateCode = generator(request);
        save(request, validateCode);
        send(request,validateCode);
    }

    /**
     * 发送校验码由子类实现
     * @param request
     * @param validateCode
     */
    protected abstract void send(ServletWebRequest request, T validateCode);

    /**
     * 保存校验码
     * @param request
     * @param validateCode
     */
    protected  void save(ServletWebRequest request, T validateCode){
        sessionStrategy.setAttribute(request,SESSION_KEY_PREFIX  + getProcessorType(request).toUpperCase(),validateCode);
    }

    protected  T generator(ServletWebRequest request){
        String type = getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(type + "ValidateCodeGenerator");
        return (T) validateCodeGenerator.generator(request);
    }

    protected String getProcessorType(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

}
