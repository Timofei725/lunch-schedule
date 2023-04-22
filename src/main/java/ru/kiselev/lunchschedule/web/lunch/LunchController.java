package ru.kiselev.lunchschedule.web.lunch;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.service.LunchService;
import ru.kiselev.lunchschedule.web.user.AuthUser;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LunchController {

    private LunchService lunchService;

    @Cacheable(value = "lunches")
    @GetMapping("/api/lunches")
    public List<Lunch> getAllTodayLunches() {
        log.info("getTodayLunches");
        return lunchService.getTodayWithUsers(LocalDate.now());
    }

    @PostMapping("/api/lunches/{lunchId}")
    @CacheEvict(value = "lunches", allEntries = true)
    public Lunch setLunchOwner(@RequestBody @Valid Lunch lunch, @PathVariable int lunchId,
                               @AuthenticationPrincipal AuthUser authUser) {
        log.info("set lunch owner for lunch {}, user {}", lunchId, authUser.getUser());
        return lunchService.setLunchOwner(lunch, authUser.getUser().getId());
    }


}
