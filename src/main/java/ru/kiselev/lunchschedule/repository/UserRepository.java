package ru.kiselev.lunchschedule.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kiselev.lunchschedule.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByEmail(String email);
}
