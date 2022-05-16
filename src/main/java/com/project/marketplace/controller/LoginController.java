package com.project.marketplace.controller;

import com.project.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("error", "Invalid email or password!");
        }
        return "login";
    }
}