package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.security.CurrentUser;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {
    private final UserService userService;
    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String managerPage(){
        return "manager";
    }

    @GetMapping("/index")
    public String showManagerIndexPage(Model model, CurrentUser currentUser) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        return "index";
    }
}
