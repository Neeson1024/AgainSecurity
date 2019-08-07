package com.hutu.core.validate;

import com.hutu.core.properties.SecurityProperties;
import com.hutu.core.validate.code.sms.*;
import com.hutu.core.validate.code.image.*;
import com.hutu.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.DefaultSecurityContextAccessor;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ValidateCodeConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ImageValidateCodeGenerator imageValidateCodeGenerator(){
        ImageValidateCodeGenerator imageValidateCodeGenerator = new ImageValidateCodeGenerator();
        imageValidateCodeGenerator.setSecurityProperties(securityProperties);
        return imageValidateCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class);
    }
}
