package ru.kiselev.lunchschedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.repository.LunchRepository;
import ru.kiselev.lunchschedule.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LunchService {
    private final LunchRepository lunchRepository;
    private final UserRepository userRepository;

    @Autowired
    public LunchService(LunchRepository lunchRepository, UserRepository userRepository) {
        this.lunchRepository = lunchRepository;
        this.userRepository = userRepository;
    }


    public List<Lunch> getAll() {
        return lunchRepository.findAll();
    }

    public List<Lunch> getTodayLunches() {
        return lunchRepository.getByDate(LocalDate.now());
    }

    public List<Lunch> createLunchesByTime(LocalTime startTime, LocalTime endTime) {
        List<Lunch> resultList = new ArrayList<>();
        List<Lunch> todayLunches = getTodayLunches();
        LocalDate today = LocalDate.now();
        while (startTime.isBefore(endTime)) {
            Lunch lunch = new Lunch();
            lunch.setStartTime(startTime);
            lunch.setDate(today);
            startTime = startTime.plusMinutes(20);
            lunch.setEndTime(startTime);
            if (isTimeCorrect(todayLunches, lunch)) {
                lunchRepository.save(lunch);
                resultList.add(lunch);
            }

        }
        return resultList;
    }


    public Lunch save(Lunch lunch) {
        return lunchRepository.save(lunch);
    }

    public boolean isTimeCorrect(List<Lunch> todayLunches, Lunch lunch) {
        return todayLunches.stream().filter(x -> x.getEndTime().isBefore(lunch.getStartTime()) || x.getStartTime().isBefore(lunch.getEndTime())).toList().size() == 0;
    }//Only one person is able to having lunch in the same time

    public List<Lunch> getByDateTime(LocalTime startTime, LocalTime endTime, LocalDate date) {
        return lunchRepository.getByDateTime(startTime, endTime, date);
    }

    public List<Lunch> getTodayLunchesByUserId(Integer id) {
        return lunchRepository.getByUserIdAndDate(id, LocalDate.now());
    }

    public Lunch setLunchOwner(Lunch lunch, int userId, int lunchId, boolean isWorkDayLong) {
        List<Lunch> thisUserLunches = getTodayLunchesByUserId(userId);
        if (isWorkDayLong) {
            if (thisUserLunches.size() <= 1) { // if person able to have 2 lunches
                lunch.setUser(userRepository.getReferenceById(userId));
                return lunchRepository.save(lunch);
            }
        } else if (thisUserLunches.size() == 0) {  // if person able to have 1 lunch
            lunch.setUser(userRepository.getReferenceById(userId));
            return lunchRepository.save(lunch);
        }
        return lunch;
    }
}


