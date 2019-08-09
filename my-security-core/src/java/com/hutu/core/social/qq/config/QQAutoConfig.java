package com.hutu.core.social.qq.config;

import com.hutu.core.properties.SecurityProperties;
import com.hutu.core.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "hutu.secuirty.socialProperties.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(securityProperties.getSocialProperties().getQq().getProviderId(),
                securityProperties.getSocialProperties().getQq().getAppId(),
                securityProperties.getSocialProperties().getQq().getAppSecret());
    }
}
