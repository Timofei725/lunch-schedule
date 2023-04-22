package ru.kiselev.lunchschedule.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.to.AuthenticationRequest;
import ru.kiselev.lunchschedule.to.RegisterRequest;
import ru.kiselev.lunchschedule.utill.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kiselev.lunchschedule.web.testdata.UserTestData.*;

class AuthenticationControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/api/auth";


    @Test
    void testRegister() throws Exception {
        User testUser = getNew();
        RegisterRequest request = new RegisterRequest(testUser.getName(), testUser.getEmail(), testUser.getPassword());
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(request)));

        actions.andExpect(status().isOk());
    }

    @Test
    void testAuthenticate() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest(user.getEmail(), user.getPassword());
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(authRequest)));

        actions.andExpect(status().isOk());
    }

    @Test
    void testAuthenticateWithInvalidCredentials() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest(user.getEmail(), "invalidPassword");
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(authRequest)));
        actions.andExpect(status().is4xxClientError());
    }
}
