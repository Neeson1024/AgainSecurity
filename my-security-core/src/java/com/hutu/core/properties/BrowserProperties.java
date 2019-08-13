package com.hutu.core.properties;

import sun.rmi.runtime.Log;

public class BrowserProperties {
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;

    private String signUpUrl = "/signUp.html";

    private LoginType loginType = LoginType.JSON;

    private int remmeberMeSeconds = 3600;

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public int getRemmeberMeSeconds() {
        return remmeberMeSeconds;
    }

    public void setRemmeberMeSeconds(int remmeberMeSeconds) {
        this.remmeberMeSeconds = remmeberMeSeconds;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
