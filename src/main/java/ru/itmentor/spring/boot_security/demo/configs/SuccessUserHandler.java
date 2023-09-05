package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.security.CurrentUser;
import ru.itmentor.spring.boot_security.demo.security.UserDetailServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itmentor.spring.boot_security.demo.model.Role.*;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    private final UserDetailServiceImpl userDetailService;

    public SuccessUserHandler(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CurrentUser currentUser = (CurrentUser) userDetailService.loadUserByUsername(authentication.getName());
        if (currentUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains(ADMIN.name()))) {
            response.sendRedirect("/admin");
        } else if (currentUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains(USER.name()))) {
            response.sendRedirect("/user");
        } else if (currentUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().contains(MANAGER.name()))) {
            response.sendRedirect("/manager");
        } else {
            response.sendRedirect("/");
        }
    }
}