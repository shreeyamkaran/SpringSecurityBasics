package com.beehyv.SpringSecurity.controller;

import com.beehyv.SpringSecurity.entity.User;
import com.beehyv.SpringSecurity.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserServiceImplementation userService;

    @PostMapping("/register/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

}
