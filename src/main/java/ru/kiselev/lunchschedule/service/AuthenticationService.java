package ru.kiselev.lunchschedule.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kiselev.lunchschedule.model.Role;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.UserRepository;
import ru.kiselev.lunchschedule.to.AuthenticationRequest;
import ru.kiselev.lunchschedule.to.AuthenticationResponse;
import ru.kiselev.lunchschedule.to.RegisterRequest;

import java.time.LocalDateTime;
import java.util.Collections;

import static ru.kiselev.lunchschedule.model.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(USER))
                .registered(LocalDateTime.now())
                .build();
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}