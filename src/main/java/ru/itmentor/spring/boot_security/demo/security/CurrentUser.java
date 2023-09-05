package ru.itmentor.spring.boot_security.demo.security;

import org.springframework.security.core.authority.AuthorityUtils;
import ru.itmentor.spring.boot_security.demo.model.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().toString()));
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
