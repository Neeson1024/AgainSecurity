package com.hutu.browser;


import com.hutu.browser.authentication.HutuAuthenticationFailHandler;
import com.hutu.browser.authentication.HutuAuthenticationSuccessHandler;
import com.hutu.core.properties.SecurityProperties;
import com.hutu.core.validate.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private HutuAuthenticationSuccessHandler hutuAuthenticationSuccessHandler;

    @Autowired
    private HutuAuthenticationFailHandler hutuAuthenticationFailHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    //public PersistentTokenRepository persistentTokenRepository(){
    //    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    //    jdbcTokenRepository.setDataSource(dataSource);
    //    jdbcTokenRepository.setCreateTableOnStartup(true);
    //    return jdbcTokenRepository;
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setHutuAuthenticationFailHandler(hutuAuthenticationFailHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(hutuAuthenticationSuccessHandler)
                .failureHandler(hutuAuthenticationFailHandler)
                .and()
                .authorizeRequests()//对请求做一个授权
                .antMatchers("/authentication/require",
                        securityProperties.getBrowserProperties().getLoginPage(),
                        "/code/image").permitAll()
                .anyRequest()//所有请求
                .authenticated()//都需要身份认证
                .and()
                .csrf().disable();

    }
}
