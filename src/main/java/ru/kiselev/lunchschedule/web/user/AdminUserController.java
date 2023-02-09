package ru.kiselev.lunchschedule.web.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.model.Role;
import ru.kiselev.lunchschedule.model.User;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminUserController extends AbstractUserController {
    static final String REST_URL = "/api/admin/users";


    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return super.repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return super.get(id);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody @Valid User user) {
        user.setRoles(Collections.singleton(Role.USER));
        log.info("creat user {}", user);
        return super.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid User user, @PathVariable int id) {
        log.info("update user {}, id {}", user, id);

        super.save(user);
    }


    @GetMapping("/by-email")
    public User getByMail(@RequestParam String email) {
        return super.repository.findByEmailIgnoreCase(email).orElse(null);
    }


}