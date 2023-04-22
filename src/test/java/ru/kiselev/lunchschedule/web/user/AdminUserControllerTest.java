package ru.kiselev.lunchschedule.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.repository.UserRepository;
import ru.kiselev.lunchschedule.web.AbstractControllerTest;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kiselev.lunchschedule.web.testdata.UserTestData.*;


class AdminUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminUserController.REST_URL;

    @Autowired
    private UserRepository userRepository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + '/' + ADMIN_ID)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + '/' + NOT_FOUND)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-email?email=" + admin.getEmail())
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + '/' + FIRST_USER_ID)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(userRepository.findById(FIRST_USER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = FIRST_USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin)))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + '/' + FIRST_USER_ID)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, "newPass")))
                .andDo(print())
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userRepository.findById(FIRST_USER_ID).get(), getUpdated());
    }

    @Test
    void create() throws Exception {
        User newUser = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, "newPass")))
                .andExpect(status().is2xxSuccessful());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userRepository.findById(newId).get(), newUser);
    }

    @Test
    void createInvalid() throws Exception {
        User invalid = new User(null, null, "", "newPass", null,0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(invalid, "newPass")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    void updateInvalid() throws Exception {
        User invalid = getUpdated();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + FIRST_USER_ID)
                .header("Authorization", "Bearer " + getAuthTokenForUser(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(invalid, "password")))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }



}

