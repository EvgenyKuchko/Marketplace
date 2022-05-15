package com.project.marketplace.controller;

import com.project.marketplace.model.User;
import com.project.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user) {
        userService.saveNewUser(user);
        return "/main";
    }
}