package ru.kiselev.lunchschedule.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.LunchRepository;
import ru.kiselev.lunchschedule.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LunchService {
    private final LunchRepository lunchRepository;
    private final UserRepository userRepository;

    public List<Lunch> getAll() {
        return lunchRepository.findAll();
    }

    public List<Lunch> createLunchesByTime(LocalTime startTime, LocalTime endTime) {
        List<Lunch> resultLunches = new ArrayList<>();
        List<Lunch> todayLunches = lunchRepository.getByDate(LocalDate.now());
        LocalDate today = LocalDate.now();
        while (startTime.isBefore(endTime)) {
            Lunch lunch = new Lunch();
            lunch.setStartTime(startTime);
            lunch.setDate(today);
            startTime = startTime.plusMinutes(20);
            lunch.setEndTime(startTime);
            if (isTimeCorrect(todayLunches, lunch)) {
                lunchRepository.save(lunch);
                resultLunches.add(lunch);
            }
        }

        return resultLunches;
    }

    public boolean isTimeCorrect(List<Lunch> todayLunches, Lunch lunch) {
        for (Lunch lunchFromList : todayLunches) {
            LocalTime start = lunchFromList.getStartTime();
            LocalTime end = lunchFromList.getEndTime();
            if (lunch.getStartTime().isBefore(end) && start.isBefore(lunch.getEndTime())) {
                return false;
            }
        }
        for (Lunch lunchFromList : todayLunches) {
            LocalTime start = lunchFromList.getStartTime();
            LocalTime end = lunchFromList.getEndTime();
            int lunchDuration = lunch.getEndTime().getMinute() - lunch.getStartTime().getMinute();
            int lunchFromListDuration = end.getMinute() - start.getMinute();

            if (lunch.getStartTime().isBefore(end) && (lunchFromListDuration < 20 || lunchDuration < 20)) {
                return false;
            }
        }
        return true;
    }

    public Lunch update(Lunch lunch, int id) {
        User user = new User();
        user.setId(id);

        lunch.setUser(user);

        return lunchRepository.save(lunch);
    }

    public List<Lunch> getByDateTimeWithUsers(LocalTime startTime, LocalTime endTime, LocalDate date) {
        if (startTime == null) {
            startTime = LocalTime.of(8, 00);
        }
        if (endTime == null) {
            endTime = LocalTime.of(23, 00);
        }
        return lunchRepository.getByDateTimeWithUsers(startTime, endTime, date);
    }

    public Lunch setLunchOwner(Lunch lunch, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        LocalDate today = LocalDate.now();
        List<Lunch> thisUserLunches = lunchRepository.getByUserIdAndDate(userId, today);

        if (user.getWorkingHours() >= 6 && thisUserLunches.isEmpty()) {
            lunch.setUser(user);
            return lunchRepository.save(lunch);
        } else if (user.getWorkingHours() > 12 && thisUserLunches.size() < 2) {

            lunch.setUser(user);
            return lunchRepository.save(lunch);
        }
        return lunch;
    }


    public Optional<Lunch> getById(int id) {
        return lunchRepository.findWithUser(id);
    }

    public void delete(int id) {
        lunchRepository.deleteById(id);
    }


    public List<Lunch> getTodayWithUsers(LocalDate localDate) {
        List<Lunch> todayLunches = lunchRepository.getByDateWithUsers(localDate);
        todayLunches.sort(Comparator.comparing(Lunch::getStartTime));
        return todayLunches;
    }

}
