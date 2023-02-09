package ru.kiselev.lunchschedule.config;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ProblemDetail;
import ru.kiselev.lunchschedule.utill.JsonUtil;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;


@Configuration
@Slf4j
public class AppConfig {


    @Autowired
    void configureAndStoreObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new Hibernate5JakartaModule());
        // https://stackoverflow.com/questions/7421474/548473
        objectMapper.addMixIn(ProblemDetail.class, MixIn.class);
        JsonUtil.setMapper(objectMapper);
    }

    //    https://stackoverflow.com/a/74630129/548473
    @JsonAutoDetect(fieldVisibility = NONE, getterVisibility = ANY)
    interface MixIn {
        @JsonAnyGetter
        Map<String, Object> getProperties();
    }


}
