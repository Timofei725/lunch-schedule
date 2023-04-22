package ru.kiselev.lunchschedule.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.kiselev.lunchschedule.model.User;
import ru.kiselev.lunchschedule.to.AuthenticationRequest;
import ru.kiselev.lunchschedule.utill.JsonUtil;


import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest()
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(value = "/cleanup-data.sql", executionPhase = AFTER_TEST_METHOD)
public abstract class AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    public String getAuthTokenForUser(User thisUser) throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest(thisUser.getEmail(), thisUser.getPassword());
        MvcResult result = perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(authRequest)))
                .andReturn();
        String response = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(response);

        return jsonObject.getString("token");
    }


}