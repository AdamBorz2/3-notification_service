package com.itm.space.notificationservice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.spring.api.DBRider;
import com.itm.space.itmplatformcommonmodels.util.AuthUtil;
import com.itm.space.itmplatformcommonmodels.util.JsonParserUtil;
import com.itm.space.notificationservice.config.UtilConfig;
import com.itm.space.notificationservice.initializer.KafkaInitializer;
import com.itm.space.notificationservice.initializer.KeycloakInitializer;
import com.itm.space.notificationservice.initializer.PostgresInitializer;
import com.itm.space.notificationservice.util.TestConsumerService;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {PostgresInitializer.class, KafkaInitializer.class, KeycloakInitializer.class})
@DBRider
@DBUnit(caseSensitiveTableNames = true, schema = "public", allowEmptyFields = true, cacheConnection = false)
@Execution(ExecutionMode.SAME_THREAD)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(UtilConfig.class)
public abstract class BaseIntegrationTest {

    @Autowired
    protected TestConsumerService testConsumerService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected AuthUtil authUtil;

    protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected JsonParserUtil jsonParserUtil = new JsonParserUtil();

    protected MockHttpServletRequestBuilder requestBuilder(MockHttpServletRequestBuilder mockHttpServletRequestBuilder, Object content) throws JsonProcessingException {
        return requestBuilder(mockHttpServletRequestBuilder).content(objectMapper.writeValueAsString(content));
    }

    protected MockHttpServletRequestBuilder requestBuilder(MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        return mockHttpServletRequestBuilder.contentType(MediaType.APPLICATION_JSON)
                .with(SecurityMockMvcRequestPostProcessors.csrf());
    }
}
