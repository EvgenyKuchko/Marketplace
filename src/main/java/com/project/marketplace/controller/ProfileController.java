package com.project.marketplace.controller;

import com.project.marketplace.model.User;
import com.project.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/{nickname}")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String path;

    @GetMapping()
    public String showProfile(@PathVariable("nickname") String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/update")
    public String showUpdateProfile(@PathVariable("nickname") String nickname, Model model) {
        User user = userService.findByNickname(nickname);
        model.addAttribute("user", user);
        return "update_profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("user") User user,
            @RequestParam("userDescription") String description,
            @RequestParam("wallet") Float wallet) throws IOException {
        User userFromDB = userService.findByNickname(user.getNickname());
        if(file != null) {
            String fileName = path + user.getNickname() + file.getOriginalFilename();
            file.transferTo(new File(fileName));
            user.setProfilePicture(fileName);
            userService.updateProfilePicture(fileName, user.getNickname());
        }
        if(!description.equals(userFromDB.getUserDescription())){
            userService.updateDescription(description, user.getNickname());
        }
        if(wallet != userFromDB.getWallet()) {
            userService.updateWallet(wallet, user.getNickname());
        }
        return "redirect:/" + user.getNickname();
    }
}