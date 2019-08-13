package com.hutu;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request){
        //注册用户
        String userId = user.getUsername();

        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
    }

    @GetMapping
    public Object create(){
        System.out.println("hello world");
        return "Hello wordl";
    }

    @GetMapping("/1")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

    @GetMapping("/2")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }

    @GetMapping("/3")
    public Object getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
