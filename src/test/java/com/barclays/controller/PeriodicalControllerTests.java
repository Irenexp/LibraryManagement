package com.barclays.controller;
import com.barclays.model.Periodical;
import com.barclays.service.PeriodicalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class PeriodicalControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Test
    public void testGettingAllPeriodicals() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/periodicals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPeriodicalsByDate() throws Exception {
        LocalDate date = LocalDate.of(2023, 1, 1);
        mockMvc.perform(MockMvcRequestBuilders.get("/periodicals/searchByDate")
                        .param("date", date.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetPeriodicalById() throws Exception {
        Integer id = 800;
        mockMvc.perform(MockMvcRequestBuilders.get("/periodicals/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreatePeriodical() throws Exception {
        Periodical periodical = new Periodical();
        periodical.setPublicationDate(LocalDate.of(2023, 11, 28));
        periodical.setTitle("Financial Times");

        mockMvc.perform(MockMvcRequestBuilders.post("/periodicals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(periodical)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePeriodical() throws Exception {
        Periodical periodical = new Periodical();

        periodical.setTitle("Shurun Times");

        Integer idToUpdate = 800;
        mockMvc.perform(MockMvcRequestBuilders.put("/periodicals/" + idToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(periodical)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePeriodical() throws Exception {
        Integer idToDelete = 800;
        mockMvc.perform(MockMvcRequestBuilders.delete("/periodicals/" + idToDelete))
                .andExpect(status().isOk());
    }
}
