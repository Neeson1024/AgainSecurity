package com.hutu.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    ValidateCode generator(ServletWebRequest request);
}
