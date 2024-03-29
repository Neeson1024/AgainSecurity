package com.hutu.core.validate.code.sms;

import com.hutu.core.properties.SecurityProperties;
import com.hutu.core.validate.ValidateCode;
import com.hutu.core.validate.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component("smsValidateCodeGenerator")
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getValidateCode().getSmsCodeProperties().getLenght());
        return new ValidateCode(code,securityProperties.getValidateCode().getImageCode().getExpireIn());
    }
}
