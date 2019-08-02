package com.hutu.core.validate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler hutuAuthenticationFailHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(StringUtils.contains(httpServletRequest.getRequestURI(),"/authentication/form")
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){

            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                e.printStackTrace();
                hutuAuthenticationFailHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
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
}
