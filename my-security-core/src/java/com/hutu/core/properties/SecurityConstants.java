package com.hutu.core.properties;

public interface SecurityConstants {

    /**
     * 默认处理验证码的url前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 当请求需要身份认证的时候，默认跳转的url
     */
    public static final String DEFAULT_UNAUTHENCATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理URL
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认手机号和验证码登录请求处理的URL
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM_MOBILE = "/authentictaion/moblie";

    /**
     * 默认登录页面
     */
    public static final String DEFAULT_LOGIN_PAGE_URL = "/signIn.html";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMATER_NAME_CODE_IMAGE = "image";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMATER_NAME_CODE_SMS = "sms";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMATER_NAME_MOBILE = "mobile";

}
