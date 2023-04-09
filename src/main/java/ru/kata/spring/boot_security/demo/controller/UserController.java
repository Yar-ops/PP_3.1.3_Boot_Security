package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String userPage(Model user, @AuthenticationPrincipal User userDetails) {
        user.addAttribute("user", userService.getUserById(userDetails.getId()).get());
        return "user/userpanel";
    }

}