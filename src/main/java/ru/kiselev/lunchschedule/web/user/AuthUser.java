package ru.kiselev.lunchschedule.web.user;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.kiselev.lunchschedule.model.User;

import java.util.Collections;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    @Getter
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    @Override
    public String toString() {
        return "AuthUser:" + user.getId() + '[' + user.getEmail() + ']';
    }
}