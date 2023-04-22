package ru.kiselev.lunchschedule.web.lunch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.service.LunchService;
import ru.kiselev.lunchschedule.to.CreateLunchesRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin/lunches")
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminLunchController {

    private final LunchService lunchService;

    @GetMapping
    public ResponseEntity<List<Lunch>> getAllLunches() {
        log.info("Getting all lunches");
        List<Lunch> lunches = lunchService.getAll();
        return ResponseEntity.ok(lunches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lunch> getLunchById(@PathVariable int id) {
        log.info("Getting lunch with id {}", id);
        Optional<Lunch> optionalLunch = lunchService.getById(id);
        if (optionalLunch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Lunch lunch = optionalLunch.get();
        return ResponseEntity.ok(lunch);
    }

    @PostMapping()
    @CacheEvict(value = "lunches", allEntries = true)
    public List<Lunch> createLunchesByTime(@Valid @RequestBody CreateLunchesRequest request) {
        log.info("create lunches since {} to {}", request.getStartTime(), request.getEndTime());
        return lunchService.createLunchesByTime(request.getStartTime(), request.getEndTime());
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "lunches", allEntries = true)
    public ResponseEntity<Lunch> updateLunch(@PathVariable int id, @RequestBody @Valid Lunch lunch) {
        log.info("Updating lunch with id {}: {}", id, lunch);
        Lunch updatedLunch = lunchService.update(lunch,id);
        return ResponseEntity.ok(updatedLunch);
    }
    @CacheEvict(value = "lunches", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLunch(@PathVariable int id) {
        log.info("Deleting lunch with id {}", id);
        Optional<Lunch> optionalLunch = lunchService.getById(id);
        if (optionalLunch.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        lunchService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Lunch>> getLunchesByDateTime(
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") @NotNull LocalDate date) {
        log.info("Getting lunches for date {}, since {} to {}", date, startTime, endTime);
        List<Lunch> lunches = lunchService.getByDateTimeWithUsers(startTime, endTime, date);
        return ResponseEntity.ok(lunches);
    }
}