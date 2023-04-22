package ru.kiselev.lunchschedule.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.kiselev.lunchschedule.model.Lunch;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LunchRepository extends BaseRepository<Lunch> {
    @Query("SELECT l from Lunch l WHERE l.date = :thisDate ORDER BY l.startTime DESC")
    List<Lunch> getByDate(@Param("thisDate") LocalDate thisDate);

    @Query("SELECT l from Lunch l LEFT JOIN FETCH l.user WHERE l.date=:date AND l.startTime >= :startTime AND l.endTime < :endTime ORDER BY l.startTime DESC")
    List<Lunch> getByDateTimeWithUsers(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime, @Param("date") LocalDate date);

    List<Lunch> getByUserIdAndDate(Integer userId, LocalDate date);

    @Query("SELECT l from Lunch l LEFT JOIN FETCH l.user WHERE l.date = :thisDate ORDER BY l.startTime DESC")
    List<Lunch> getByDateWithUsers(@Param("thisDate") LocalDate thisDate);

    @Query("SELECT l FROM Lunch l LEFT JOIN FETCH l.user where l.id=:id")
    Optional<Lunch> findWithUser(@Param("id") Integer id);
}