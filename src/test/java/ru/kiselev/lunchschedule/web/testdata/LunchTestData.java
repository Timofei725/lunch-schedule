package ru.kiselev.lunchschedule.web.testdata;

import ru.kiselev.lunchschedule.model.Lunch;
import ru.kiselev.lunchschedule.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.kiselev.lunchschedule.web.testdata.UserTestData.user;
import static ru.kiselev.lunchschedule.web.testdata.UserTestData.userSecond;

public class LunchTestData {

    public static final MatcherFactory.Matcher<Lunch> LUNCH_MATCHER = MatcherFactory.usingEqualsComparator(Lunch.class);
    private LocalTime endTime;
    private LocalDate date;
    public static final Lunch firstUserLunch = new Lunch(1, LocalTime.of(12, 00), LocalTime.of(12, 20), LocalDate.of(2023, 2, 12), user);
    public static final Lunch secondUserFirstLunch = new Lunch(2, LocalTime.of(12, 20), LocalTime.of(12, 40), LocalDate.of(2023, 2, 12), userSecond);
    public static final Lunch secondUserSecondLunch = new Lunch(3, LocalTime.of(17, 00), LocalTime.of(17, 20), LocalDate.of(2023, 2, 12), userSecond);


    public static Lunch getNew() {
        return new Lunch(1, LocalTime.of(15, 00), LocalTime.of(15, 20), LocalDate.of(2023, 2, 12));
    }

    public static Lunch getUpdated() {
        Lunch lunch = new Lunch(firstUserLunch.getId(), LocalTime.of(16, 00), LocalTime.of(16, 20), firstUserLunch.getDate());
        user.setRegistered(LocalDateTime.now());
        return lunch;
    }
}

