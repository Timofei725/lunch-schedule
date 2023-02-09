package ru.kiselev.lunchschedule.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity

@Table(name = "lunch",
        indexes = @Index(name = "lunchIndex", columnList = "startTime,endTime,date", unique = true))
//One unique lunch per day
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
public class Lunch extends BaseEntity {
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Lunch(Integer id, LocalTime startTime, LocalTime endTime, LocalDate date) {
        super(id);
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
}
