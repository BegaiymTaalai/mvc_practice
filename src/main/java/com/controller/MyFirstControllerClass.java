package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MyFirstControllerClass {

    @GetMapping("/site")
    public String getFirstHtml(){
        return "company/test";
    }

    @GetMapping("/image")
    public String getSecondHtml(){
        return "user/helloworld";
    }

}
