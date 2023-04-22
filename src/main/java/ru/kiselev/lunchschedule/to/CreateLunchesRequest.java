package ru.kiselev.lunchschedule.to;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLunchesRequest {
    @NotNull(message = "не должно равняться null")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "не должно равняться null")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;


}