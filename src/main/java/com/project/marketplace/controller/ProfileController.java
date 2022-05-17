package com.project.marketplace.controller;

import com.project.marketplace.model.User;
import com.project.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/{nickname}")
    public String showProfile(@PathVariable("nickname") String nickname, Model model){
        User user = userService.findByNickname(nickname);
        model.addAttribute("user", user);
        return "profile";
    }
}