package com.hutu.browser;


import com.hutu.browser.authentication.HutuAuthenticationFailHandler;
import com.hutu.browser.authentication.HutuAuthenticationSuccessHandler;
import com.hutu.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private HutuAuthenticationSuccessHandler hutuAuthenticationSuccessHandler;

    @Autowired
    private HutuAuthenticationFailHandler hutuAuthenticationFailHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(hutuAuthenticationSuccessHandler)
                .failureHandler(hutuAuthenticationFailHandler)
                .and()
                .authorizeRequests()//对请求做一个授权
                .antMatchers("/authentication/require",securityProperties.getBrowserProperties().getLoginPage()).permitAll()
                .anyRequest()//所有请求
                .authenticated()//都需要身份认证
                .and()
                .csrf().disable();

    }
}
