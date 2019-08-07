package com.hutu.core.validate;

import com.hutu.core.properties.SecurityConstants;
import com.hutu.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler hutuAuthenticationFailHandler;

    @Autowired
    private AuthenticationSuccessHandler hutuauthenticationSuccessHandler;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 存放所有需要校验码的url
     */
    private Map<String,ValidateCodeType> urls = new HashMap();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getValidateCode().getImageCode().getUrls(),ValidateCodeType.IMAGE);

        urls.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getValidateCode().getSmsCodeProperties().getUrls(),ValidateCodeType.SMS);
    }

    protected void addUrlToMap(String urlString,ValidateCodeType type){
        if(StringUtils.isNotBlank(urlString)){
            String[] split = urlString.split(",");
            for(String url : split){
                urls.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(httpServletRequest);
        if(type != null){
            logger.info("校验请求(" + httpServletRequest.getRequestURI() + ")中的验证码,验证码类型" + type);

            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
            }catch (ValidateCodeException e){
                hutuAuthenticationFailHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest httpServletRequest) {
        ValidateCodeType result = null;
        if(!StringUtils.equalsAnyIgnoreCase(httpServletRequest.getMethod(),"get")){
            Set<String> url_set = urls.keySet();
            for(String url : url_set){
                if(antPathMatcher.match(httpServletRequest.getRequestURI(),url)){
                    result = urls.get(url);
                    break;
                }
            }
        }
        return result;
    }


    //todo 老版本
    //private Set<String> urls = new HashSet<>();

    //@Override
    //public void afterPropertiesSet() throws ServletException {
    //    super.afterPropertiesSet();
    //    String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getValidateCode().getImageCode().getUrls(), ",");
    //    for(String url : configUrls){
    //        urls.add(url);
    //    }
    //    urls.add("/authentication/form");
    //}
    //
    //@Override
    //protected void doFilterInternal(HttpServletRequest httpServletRequest,
    //                                HttpServletResponse httpServletResponse,
    //                                FilterChain filterChain) throws ServletException, IOException {
    //
    //    boolean action = false;
    //
    //    for(String str : urls){
    //        if(antPathMatcher.match(str,httpServletRequest.getRequestURI())){
    //            action = true;
    //            break;
    //        }
    //    }
    //    if(action) {
    //        try {
    //            validate(new ServletWebRequest(httpServletRequest));
    //        } catch (ValidateCodeException e) {
    //            e.printStackTrace();
    //            hutuAuthenticationFailHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    //            return;
    //        }
    //    }
    //
    //    filterChain.doFilter(httpServletRequest,httpServletResponse);
    //
    //}
    //
    //private void validate(ServletWebRequest request)throws ValidateCodeException {
    //    String imageCode = request.getParameter("imageCode");
    //    ImageCode attribute = (ImageCode)sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX );
    //
    //    if(StringUtils.isBlank(imageCode)){
    //        throw new ValidateCodeException("验证码不能为空");
    //    }
    //
    //    if(attribute.isExpire()){
    //        throw new ValidateCodeException("验证码已经过期");
    //    }
    //
    //    if(!StringUtils.equalsIgnoreCase(attribute.getCode(),imageCode)){
    //        throw new ValidateCodeException("验证码不一致");
    //    }
    //
    //
    //    sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX );
    //}
    //
    //public void setHutuAuthenticationFailHandler(AuthenticationFailureHandler hutuAuthenticationFailHandler) {
    //    this.hutuAuthenticationFailHandler = hutuAuthenticationFailHandler;
    //}
    //
    //public void setSecurityProperties(SecurityProperties securityProperties) {
    //    this.securityProperties = securityProperties;
    //}


}
