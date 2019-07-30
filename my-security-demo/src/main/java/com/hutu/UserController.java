package com.hutu;

import com.sun.org.apache.xpath.internal.operations.String;
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
}
