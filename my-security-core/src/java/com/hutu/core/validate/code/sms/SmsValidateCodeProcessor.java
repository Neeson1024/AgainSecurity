package com.hutu.core.validate.code.sms;

import com.hutu.core.validate.ValidateCode;
import com.hutu.core.validate.impl.AbstractValidateCodeProcessor;
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

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) {
        try {
            String mobile = ServletRequestUtils.getRequiredStringParameter((ServletRequest)request.getRequest(), "mobile");
            smsCodeSender.send(mobile,validateCode.getCode());
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
    }
}
