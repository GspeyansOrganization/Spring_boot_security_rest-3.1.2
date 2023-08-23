package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exception.IncorrectPasswordException;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.security.CurrentUser;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;

import static ru.itmentor.spring.boot_security.demo.model.Role.MANAGER;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String UsersView(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping("/register")
    public String loginPage(Model model, User user) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.saveUser(user, false);
        return "redirect:/signin";
    }

    @GetMapping("/signin")
    public String showLoginForm() {
        return "signin";
    }

    @PostMapping("/signin")
    public String processLoginForm() {
        return "signin";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @GetMapping(value = "/update/{id}")
    public String showUpdateForm(@PathVariable(value = "id") long id, Model model, CurrentUser currentUser) {
        User user = userService.getUserById(id).orElseThrow();
        Set<Role> roles = Set.of(Role.values());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("currentUser", currentUser);
        return "updateUser";
    }

    @PostMapping(value = "/updateUser")
    public String updateForm(@ModelAttribute("user") User user, long id) {
        userService.editUser(user, id);
        return "redirect:/admin/index";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/index";
    }

    @GetMapping(value = "/changeCredentials")
    public String showUpdateForm(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = currentUser.getUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        return "changeCredentials";
    }

    @PostMapping(value = "/changeCredentials")
    public String updateForm(@ModelAttribute("user") User user,
                             Model model,
                             @RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword) {
        try {
            userService.updateUser(user, oldPassword, newPassword);
            return user.getRoles().contains(MANAGER) ? "redirect:/manager/index" : "redirect:/user/index";
        } catch (IncorrectPasswordException e) {
            model.addAttribute("passwordError", true);
            return "changeCredentials";
        }
    }
}
