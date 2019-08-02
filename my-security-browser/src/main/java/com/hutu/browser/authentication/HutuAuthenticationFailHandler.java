package com.hutu.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hutu.browser.suppot.SimpleResponse;
import com.hutu.core.properties.LoginType;
import com.hutu.core.properties.SecurityProperties;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("hutuAuthenticationFailHandler")
public class HutuAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");
        if(LoginType.JSON.equals(securityProperties.getBrowserProperties().getLoginType())) {
            httpServletResponse.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        }else{
            super.onAuthenticationFailure(httpServletRequest, httpServletResponse,e);
            return;
        }
    }
}
