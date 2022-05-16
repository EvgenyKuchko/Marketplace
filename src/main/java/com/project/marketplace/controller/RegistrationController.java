package com.project.marketplace.controller;

import com.project.marketplace.exception.EmailException;
import com.project.marketplace.exception.NicknameException;
import com.project.marketplace.exception.PasswordException;
import com.project.marketplace.model.User;
import com.project.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user, Model model) throws Exception {
        try {
            userService.saveNewUser(user);
        } catch (NicknameException ex) {
            model.addAttribute("nicknameError", ex.getMessage());
            return "registration";
        } catch (EmailException ex) {
            model.addAttribute("emailError", ex.getMessage());
            return "registration";
        } catch (PasswordException ex) {
            model.addAttribute("passwordError", ex.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}