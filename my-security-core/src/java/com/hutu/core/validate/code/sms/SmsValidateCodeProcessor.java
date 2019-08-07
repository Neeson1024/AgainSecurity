package com.hutu.core.validate.code.sms;

import com.hutu.core.validate.ValidateCode;
import com.hutu.core.validate.code.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletRequest;

@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            String mobile = ServletRequestUtils.getRequiredStringParameter((ServletRequest)request.getRequest(), "mobile");
            logger.info("向手机号:" + mobile + "发送验证码" + validateCode.getCode());
            smsCodeSender.send(mobile,validateCode.getCode());
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
    }
}
