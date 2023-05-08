package ru.kiselev.lunchschedule.web.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.UserRepository;
import ru.kiselev.lunchschedule.utill.JsonUtil;
import ru.kiselev.lunchschedule.web.AbstractControllerTest;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kiselev.lunchschedule.web.testdata.UserTestData.*;

class ProfileControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void get() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get(ProfileController.REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(user)));
        actions.andExpect(status().isOk());
        actions.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }


    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ProfileController.REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(user)))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findAll(), admin, userSecond);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(ProfileController.REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "newPass")))
                .andDo(print()).andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.findById(FIRST_USER_ID).get(), getUpdated());
    }


    @Test
    void updateInvalid() throws Exception {
        User updatedTo = new User(null, null, "password", null, null, 0);
        perform(MockMvcRequestBuilders
                .put(ProfileController.REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status()
                        .is4xxClientError());
    }

}

