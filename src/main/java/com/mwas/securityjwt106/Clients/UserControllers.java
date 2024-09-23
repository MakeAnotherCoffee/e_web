package com.mwas.securityjwt106.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllers {
    @Autowired
    private UserServices userServices;
    @GetMapping("/admin/home")
    public String adminPage(){
        return "admin page";
    }
    @GetMapping("/user/home")
    public String userPage(){
        return "user page";
    }
    @GetMapping("")
    public String homePage(){
        return "home page";
    }
    @PostMapping("/registers")
    public Web_Users register(@RequestBody Web_Users webUsers){
        return userServices.registerUser(webUsers);
    }
}
