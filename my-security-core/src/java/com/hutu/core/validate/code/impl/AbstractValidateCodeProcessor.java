package com.hutu.core.validate.code.impl;

import com.hutu.core.validate.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

@Component
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {

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


    /**
     * 根据请求的url获取校验码的类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request){
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type);
    }


    /**
     * 构建验证码放入session时的key
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request){
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }


    /**
     * 校验验证码
     * @param request
     */
    public void validate(ServletWebRequest request){
        ValidateCodeType validateCodeType = getValidateCodeType(request);

        String sessionKey = getSessionKey(request);

        T codeInSession = (T)sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), validateCodeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if(StringUtils.isNotBlank(codeInRequest)){
            throw new ValidateCodeException(validateCodeType + "验证码的值不能为空");
        }

        if(codeInSession.isExpire()){
            sessionStrategy.removeAttribute(request,sessionKey);
            throw new ValidateCodeException(validateCodeType + "验证码已过期");
        }

        if(StringUtils.equals(codeInSession.getCode(),codeInRequest)){
            throw new ValidateCodeException(validateCodeType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,sessionKey);
    }
}
