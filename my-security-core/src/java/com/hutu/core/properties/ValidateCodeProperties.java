package com.hutu.core.properties;

public class ValidateCodeProperties {
    private ImageCodeProperties imageCode = new ImageCodeProperties();

    private SmsCodeProperties smsCodeProperties = new SmsCodeProperties();

    public SmsCodeProperties getSmsCodeProperties() {
        return smsCodeProperties;
    }

    public void setSmsCodeProperties(SmsCodeProperties smsCodeProperties) {
        this.smsCodeProperties = smsCodeProperties;
    }

    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }
}
