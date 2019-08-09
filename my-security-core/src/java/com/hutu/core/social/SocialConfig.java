package com.hutu.core.social;

import com.hutu.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        /**
         * dataSource:数据源
         * connectionFactoryLocator:决定使用哪一个connectionFactoryLocator
         *  Encryptors.noOpText():加解密
         *
         */
        return new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    public SpringSocialConfigurer hutuSpringSocialConfigurer(){
        HutuSpringSocialConfigurer hutuSpringSocialConfigurer = new HutuSpringSocialConfigurer(securityProperties.getSocialProperties().getFilterProcessesUrl());
        return hutuSpringSocialConfigurer;
        //http%3A%2F%2Fwww.pinzhi365.com%2Fauth%2Fqq&state=8eecf375-70ed-44b6-a8c0-901635bca97d
        //return new SpringSocialConfigurer();
    }
}
