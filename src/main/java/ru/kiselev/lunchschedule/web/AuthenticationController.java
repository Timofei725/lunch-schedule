package ru.kiselev.lunchschedule.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.service.AuthenticationService;
import ru.kiselev.lunchschedule.to.AuthenticationRequest;
import ru.kiselev.lunchschedule.to.AuthenticationResponse;
import ru.kiselev.lunchschedule.to.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


    public static void main(String[] args) {
        char[] efe= {'w','d'};
        String string = new String(efe);
        System.out.println(string);

    }
}