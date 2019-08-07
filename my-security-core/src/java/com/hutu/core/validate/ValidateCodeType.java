package com.hutu.core.validate;

import com.hutu.core.properties.SecurityConstants;
import org.springframework.security.core.context.SecurityContextHolder;

public enum ValidateCodeType {

    IMAGE{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMATER_NAME_CODE_IMAGE;
        }
    },

    SMS{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMATER_NAME_CODE_SMS;
        }
    };


    public abstract String getParamNameOnValidate();
}
