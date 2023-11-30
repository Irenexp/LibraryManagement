package com.barclays.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.barclays.dto.BorrowedBookDTO;
import com.barclays.model.*;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class BorrowedBookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Test
    public void testBorrowBook() throws Exception {
        int bookId = 300;
        int readerId = 400;

        MvcResult result = mockMvc.perform(post("/borrowBooks/borrow")
                        .param("bookId", String.valueOf(bookId))
                        .param("readerId", String.valueOf(readerId)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        BorrowedBookDTO borrowedBookDTO = mapper.readValue(contentAsString, BorrowedBookDTO.class);

        assertEquals(bookId, borrowedBookDTO.getBookId());
        assertEquals(readerId, borrowedBookDTO.getReaderId());
        assertNotNull(borrowedBookDTO.getBorrowDate());
    }

    @Test
    public void testReturnBook() throws Exception {
        Integer borrowedBookId = 100;

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/borrowBooks/return/" + borrowedBookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        BorrowedBook returnedBook = mapper.readValue(contentAsString, BorrowedBook.class);

        assertNotNull(returnedBook.getReturnDate());
    }

    @Test
    public void testGetCurrentBorrowedBooks() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrowBooks/current")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        BorrowedBook[] borrowedBooks = mapper.readValue(contentAsString, BorrowedBook[].class);

        for (BorrowedBook borrowedBook : borrowedBooks) {
            assertNotNull(borrowedBook.getBorrowDate());
            assertNull(borrowedBook.getReturnDate());
        }
    }
}
