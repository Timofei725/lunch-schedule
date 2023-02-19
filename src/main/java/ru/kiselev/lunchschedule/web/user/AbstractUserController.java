package ru.kiselev.lunchschedule.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.UserRepository;

import java.util.Optional;

@Slf4j
public class AbstractUserController {
    @Autowired
    protected UserRepository repository;

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteById(id);
    }

    protected Optional<User> save(User user) {
        return Optional.of(repository.save(user));
    }
}
