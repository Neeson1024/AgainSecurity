package com.hutu;

import com.sun.org.apache.xpath.internal.operations.String;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

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
