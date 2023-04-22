package ru.kiselev.lunchschedule.web.testdata;

import ru.kiselev.lunchschedule.model.Role;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.utill.JsonUtil;
import ru.kiselev.lunchschedule.web.MatcherFactory;

import java.util.Collections;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password","lunches");

    public static final int FIRST_USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int SECOND_USER_ID = 3;
    public static final int NOT_FOUND = 1001;

    public static final String FIRST_USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String SECOND_USER_MAIL = "user2@gmail.com";

    public static final User user = new User(FIRST_USER_ID, "User_First", FIRST_USER_MAIL, "password", Collections.singleton(Role.USER),7);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Collections.singleton(Role.ADMIN),12);
    public static final User userSecond = new User(SECOND_USER_ID, "User_Second", SECOND_USER_MAIL, "password2", Collections.singleton(Role.USER),16);


    public static User getNew() {
        return new User(null, "NewUser", "newEmail@gmail.com", "newPass", Collections.singleton(Role.USER),13);
    }

    public static User getUpdated() {
        User UpdatedUser = new User(FIRST_USER_ID, "UpdatedName", FIRST_USER_MAIL, "newPass", Collections.singleton(Role.USER),14);
        UpdatedUser.setId(user.getId());
        return UpdatedUser;
    }


    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}

