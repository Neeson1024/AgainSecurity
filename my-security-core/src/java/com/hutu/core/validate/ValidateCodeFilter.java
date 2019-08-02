package com.hutu.core.validate;

import com.hutu.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler hutuAuthenticationFailHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getValidateCode().getImageCode().getUrls(), ",");
        for(String url : configUrls){
            urls.add(url);
        }
        urls.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;

        for(String str : urls){
            if(antPathMatcher.match(str,httpServletRequest.getRequestURI())){
                action = true;
                break;
            }
        }
        if(action) {
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                e.printStackTrace();
                hutuAuthenticationFailHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    private void validate(ServletWebRequest request)throws ValidateCodeException {
        String imageCode = request.getParameter("imageCode");
        ImageCode attribute = (ImageCode)sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);

        if(StringUtils.isBlank(imageCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(attribute.isExpire()){
            throw new ValidateCodeException("验证码已经过期");
        }

        if(!StringUtils.equalsIgnoreCase(attribute.getCode(),imageCode)){
            throw new ValidateCodeException("验证码不一致");
        }


        sessionStrategy.removeAttribute(request,ValidateCodeController.SESSION_KEY);
    }

    public void setHutuAuthenticationFailHandler(AuthenticationFailureHandler hutuAuthenticationFailHandler) {
        this.hutuAuthenticationFailHandler = hutuAuthenticationFailHandler;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }


}
