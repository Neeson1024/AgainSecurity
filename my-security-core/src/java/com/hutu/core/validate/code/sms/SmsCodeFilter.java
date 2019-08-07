package com.hutu.core.validate.code.sms;

import com.hutu.core.properties.SecurityProperties;
import com.hutu.core.validate.ValidateCode;
import com.hutu.core.validate.ValidateCodeException;
import com.hutu.core.validate.ValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
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

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler hutuAuthenticationFailHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private SecurityProperties securityProperties;

    private Set<String> urls = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if(StringUtils.isNotBlank(securityProperties.getValidateCode().getSmsCodeProperties().getUrls())) {
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getValidateCode().getSmsCodeProperties().getUrls(), ",");
            for (String url : configUrls) {
                urls.add(url);
            }
        }
        urls.add("/authentication/mobile");
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
        ValidateCode validateCode = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS");
        String imageCode = request.getParameter("smsCode");
        ValidateCode attribute = (ValidateCode)sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX  + "SMS");

        if(StringUtils.isBlank(imageCode)){
            throw new ValidateCodeException("验证码不能为空");
        }

        if(attribute.isExpire()){
            throw new ValidateCodeException("验证码已经过期");
        }

        if(!StringUtils.equalsIgnoreCase(attribute.getCode(),imageCode)){
            throw new ValidateCodeException("验证码不一致");
        }


        sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX + "SMS" );
    }

    public void setHutuAuthenticationFailHandler(AuthenticationFailureHandler hutuAuthenticationFailHandler) {
        this.hutuAuthenticationFailHandler = hutuAuthenticationFailHandler;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }


}
