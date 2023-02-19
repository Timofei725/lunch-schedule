package ru.kiselev.lunchschedule.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.service.LunchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface LunchRepository extends BaseRepository<Lunch> {
    @Query("SELECT l from Lunch l WHERE l.date = :thisDate ORDER BY l.startTime DESC")
    List<Lunch> getByDate(@Param("thisDate") LocalDate thisDate);

    @Query("SELECT l from Lunch l WHERE l.date=:date AND l.startTime >= :startTime AND l.endTime < :endTime ORDER BY l.startTime DESC")
    List<Lunch> getByDateTime(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime, @Param("date") LocalDate date);

    List<Lunch> getByUserIdAndDate(Integer userId, LocalDate date);


    @Query("SELECT DISTINCT l FROM Lunch l LEFT JOIN FETCH l.user")
    List<Lunch> findAllWithUsers();

    @Query("SELECT l FROM Lunch l LEFT JOIN FETCH l.user where l.id=:id")
    Optional<Lunch> findWithUser(@Param("id") Integer id);
}