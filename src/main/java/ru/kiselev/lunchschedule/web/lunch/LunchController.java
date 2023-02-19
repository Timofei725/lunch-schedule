package ru.kiselev.lunchschedule.web.lunch;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.service.LunchService;
import ru.kiselev.lunchschedule.web.user.AuthUser;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")

public class LunchController {

    private LunchService lunchService;

    @GetMapping("/api/lunches")
    public List<Lunch> getAllTodayLunches() {
        log.info("getTodayLunches");
        return lunchService.getTodayLunches();
    }

    @GetMapping("/api/profile/{userId}/lunches")
    public List<Lunch> getUserLunches(@PathVariable int userId) {
        log.info("get lunches for user {}", userId);
        return lunchService.getTodayLunchesByUserId(userId);
    }

    @PutMapping("/api/lunches/{lunchId}")
    public Lunch setLunchOwner(@RequestBody @Valid Lunch lunch, @PathVariable int lunchId,
                               @AuthenticationPrincipal User user) {
        log.info("set lunch owner for lunch {}, user {}", lunchId, user);
        return lunchService.setLunchOwner(lunch, user.getId());
    }




}
