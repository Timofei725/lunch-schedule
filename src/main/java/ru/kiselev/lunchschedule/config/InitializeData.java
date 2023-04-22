package ru.kiselev.lunchschedule.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.model.Role;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.LunchRepository;
import ru.kiselev.lunchschedule.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializeData implements CommandLineRunner {
    private final LunchRepository lunchRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(String... args)  {
        // creating users

        User user = new User(1, "User_First", "user@gmail.com", passwordEncoder.encode("password"), Collections.singleton(Role.USER), 7);
        User admin = new User(2, "Admin", "admin@gmail.com", passwordEncoder.encode("admin"), Collections.singleton(Role.ADMIN), 12);
        User userSecond = new User(3, "User_Second", "user2@gmail.com", passwordEncoder.encode("password1"), Collections.singleton(Role.USER), 16);
        userRepository.saveAll(List.of(user,admin,userSecond));





        Lunch firstUserLunch = new Lunch(1, LocalTime.of(12, 00), LocalTime.of(12, 20), LocalDate.now());
         Lunch secondUserFirstLunch = new Lunch(2, LocalTime.of(12, 20), LocalTime.of(12, 40), LocalDate.now());
         Lunch secondUserSecondLunch = new Lunch(3, LocalTime.of(17, 00), LocalTime.of(17, 20), LocalDate.now());
        lunchRepository.saveAll(List.of(firstUserLunch,secondUserFirstLunch,secondUserSecondLunch));





    }
}


