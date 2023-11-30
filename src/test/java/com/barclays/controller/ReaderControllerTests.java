package com.barclays.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.barclays.model.Address;
import com.barclays.model.Book;
import com.barclays.model.Reader;
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
public class ReaderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Test
     void testGettingAllMovies() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/readers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Reader[] reader = mapper.readValue(contentAsString, Reader[].class);

        int expectedLength = 6;
        assertAll("Testing get all movies",
                () -> assertEquals(expectedLength, reader.length),
                () -> assertNotNull(reader[0].getName()));
    }

    @Test
    void testGetReaderById() throws Exception {
        int readerId = 100;

        MvcResult result = mockMvc.perform(get("/readers/" + readerId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Reader reader = mapper.readValue(contentAsString, Reader.class);

        assertEquals(readerId, reader.getId());
    }

    @Test
    void testGetBorrowedBooksByReader() throws Exception {
        int readerId = 100;

        MvcResult result = mockMvc.perform(get("/readers/" + readerId + "/borrowedBooks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Book[] borrowedBooks = mapper.readValue(contentAsString, Book[].class);

        assertTrue(borrowedBooks.length >= 1);
    }

    @Test
    void testCreateReader() throws Exception {
        Reader newReader = new Reader();
        Address address = new Address();

        address.setLineOne("123 Main St");
        address.setLineTwo("Apt 4");
        address.setState("Anystate");
        address.setCountry("Country");
        address.setPostalCode("12345");

        newReader.setName("John Doe");
        newReader.setPhoneNumber("555-1234");
        newReader.setEmail("john.doe@example.com");
        newReader.setAddress(address);

        String jsonRequest = mapper.writeValueAsString(newReader);

        MvcResult result = mockMvc.perform(post("/readers")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Reader savedReader = mapper.readValue(contentAsString, Reader.class);

        assertNotNull(savedReader.getId());
        assertEquals("John Doe", savedReader.getName());
    }

    @Test
    void testUpdateReader() throws Exception {

        Address updatedAddress = new Address();
        updatedAddress.setLineOne("456 Secondary St");
        updatedAddress.setLineTwo("Apt 5");
        updatedAddress.setState("Newstate");
        updatedAddress.setCountry("Newcountry");
        updatedAddress.setPostalCode("54321");


        Reader updatedReader = new Reader();
        updatedReader.setName("Jane Smith");
        updatedReader.setPhoneNumber("555-6789");
        updatedReader.setEmail("jane.smith@example.com");
        updatedReader.setAddress(updatedAddress);

        String jsonRequest = mapper.writeValueAsString(updatedReader);
        int readerIdToUpdate = 100;

        MvcResult result = mockMvc.perform(put("/readers/" + readerIdToUpdate)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Reader reader = mapper.readValue(contentAsString, Reader.class);

        assertEquals(readerIdToUpdate, reader.getId());
        assertEquals("Jane Smith", reader.getName());
    }

    @Test
    void testDeleteReader() throws Exception {
        int readerIdToDelete = 100;

        mockMvc.perform(delete("/readers/" + readerIdToDelete))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/readers/" + readerIdToDelete)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
