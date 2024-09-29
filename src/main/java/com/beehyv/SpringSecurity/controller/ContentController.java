package com.beehyv.SpringSecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/home")
    public String homePage() {
        return "Home";
    }

    @GetMapping("/user/home")
    public String userHomePage() {
        return "UserHome.html";
    }

    @GetMapping("/admin/home")
    public String adminHomePage() {
        return "AdminHome";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "Logout";
    }
}
