package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/adminpanel";
    }

    @GetMapping("/redactor/{id}")
    public String getAdminRedactor(Model user, Model roles, @PathVariable("id") Long id) {
        roles.addAttribute("allRoles", roleService.findAll());
        user.addAttribute("user", userService.getUserById(id).get());
        return "admin/edituser";
    }

    @PatchMapping("/redactor/{id}")
    public String patchAdminRedactor(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.adminRedactor(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String adminDelete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/registration")
    public String registrationGet(@ModelAttribute("newuser") User user, Model roles) {
        roles.addAttribute("allRoles", roleService.findAll());
        return "/admin/registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("newuser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}