package com.personaltrainer.controller;

import com.personaltrainer.model.User;
import com.personaltrainer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String hello() {

        return "hello from back ";
    }


    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {

        return userService.findAll();
    }


}
