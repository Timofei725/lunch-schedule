package ru.kiselev.lunchschedule.web.user;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

import static ru.kiselev.lunchschedule.utill.ValidationUtil.assureIdConsistent;

@Slf4j
public class AbstractUserController {
    @Autowired
    protected UserRepository repository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }


    protected Optional<User> save(User user) {
        if (StringUtils.isEmpty(user.getPassword())) {
            User savedUser = repository.findById(user.getId()).orElse(null);
            user.setPassword(savedUser.getPassword());
        } else user.setPassword(passwordEncoder.encode(user.getPassword()));


        return Optional.of(repository.save(user));
    }
}
