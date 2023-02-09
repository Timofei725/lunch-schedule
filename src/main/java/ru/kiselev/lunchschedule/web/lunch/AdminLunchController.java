package ru.kiselev.lunchschedule.web.lunch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.service.LunchService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = AdminLunchController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminLunchController {
    static final String REST_URL = "/api/admin/lunches";
    private LunchService lunchService;

    @GetMapping
    public List<Lunch> getAll() {
        log.info("getALl");
        return lunchService.getAll();
    }


    @GetMapping("/filter")
    public List<Lunch> getFiltered(@RequestParam @DateTimeFormat(pattern = "HH:mm") @NotNull LocalTime startTime,
                                   @RequestParam @DateTimeFormat(pattern = "HH:mm") @NotNull LocalTime endTime,
                                   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") @NotNull LocalDate date) {
        log.info("get lunches for date {}, since {} to {}", date, startTime, endTime);
        return lunchService.getByDateTime(startTime, endTime, date);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Lunch> createLunchesByTime(@RequestParam @DateTimeFormat(pattern = "HH:mm") @NotNull LocalTime startTime,
                                           @RequestParam @DateTimeFormat(pattern = "HH:mm") @NotNull LocalTime endTime) {
        log.info("create lunches since {} to {}", startTime, endTime);
        return lunchService.createLunchesByTime(startTime, endTime);
    }

    @PutMapping(value = "/{lunchId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid Lunch lunch, @PathVariable int lunchId) {
        log.info("update {} with id={}", lunch, lunch);
        lunchService.save(lunch);
    }

}


