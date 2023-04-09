package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userServiceImpl;
    private final RoleServiceImp roleServiceImpl;

    public AdminController(UserServiceImp userService, RoleServiceImp roleServiceImp) {
        this.userServiceImpl = userService;
        this.roleServiceImpl = roleServiceImp;
    }

    @GetMapping("")
    public String adminPage(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "admin/adminpanel";
    }

    @GetMapping("/redactor/{id}")
    public String getAdminRedactor(Model user, Model roles, @PathVariable("id") Long id) {
        roles.addAttribute("allRoles", roleServiceImpl.findAll());
        user.addAttribute("user", userServiceImpl.getUserById(id).get());
        return "admin/edituser";
    }

    @PatchMapping("/redactor/{id}")
    public String patchAdminRedactor(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userServiceImpl.adminRedactor(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String adminDelete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/registration")
    public String registrationGet(@ModelAttribute("newuser") User user, Model roles) {
        roles.addAttribute("allRoles", roleServiceImpl.findAll());
        return "/admin/registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("newuser") User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin";
    }
}