package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.security.CurrentUser;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

import static ru.itmentor.spring.boot_security.demo.model.Role.ADMIN;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/admin/index")
    public String showAdminIndexPage(Model model, CurrentUser currentUser) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "index";
    }

    @GetMapping(value = "/admin/saveUser")
    public String showAddUserForm(Model model) {
        User user = new User();
        user.setRoles(Set.of(Role.values()));
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping(value = "/saveUser")
    public String addUser(@ModelAttribute("user") User user, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null && currentUser.getUser().getRoles().contains(ADMIN)) {
            userService.saveUser(user, true);
        }
        return "redirect:/admin/index";
    }
}
