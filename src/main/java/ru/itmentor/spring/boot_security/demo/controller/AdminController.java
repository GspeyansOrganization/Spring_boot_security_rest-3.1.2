package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.security.CurrentUser;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

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
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user, true);
        return "redirect:/admin/index";
    }
}
