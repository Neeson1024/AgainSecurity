package com.hutu.core.validate;

import com.hutu.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

@RestController
public class ValidateCodeController {

//    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
//
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    @Autowired
//    private ValidateCodeGenerator imageValidateCodeGenerator;
//
//    @Autowired
//    private ValidateCodeGenerator SmsValidateCodeGenerator;
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;

//    @GetMapping("/code/image")
//    public void createCode(HttpServletRequest request, HttpServletResponse  response) throws IOException {
//        ImageCode imageCode = (ImageCode) imageValidateCodeGenerator.generator(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
//        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
//    }
//
//    @GetMapping("/code/sms")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletRequestBindingException {
//        ValidateCode smsCode = SmsValidateCodeGenerator.generator(new ServletWebRequest(request));
//        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
//        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
//        smsCodeSender.send(mobile,smsCode.getCode());
//    }

    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessorMap;

    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type){
        validateCodeProcessorMap.get(type + "ValidateCodeProcessor").create(new ServletWebRequest(request,response));
    }

}
