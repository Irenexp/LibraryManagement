package com.barclays.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.barclays.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class AuthorControlletTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGettingAllAuthors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testDeleteAuthorById() throws Exception {
        Integer authorIdToDelete = 100;

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/" + authorIdToDelete))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/" + authorIdToDelete)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setAuthorName("New Author");

        String jsonRequest = objectMapper.writeValueAsString(author);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Author savedAuthor = objectMapper.readValue(jsonResponse, Author.class);

        assertAll("Author properties",
                () -> assertEquals(author.getAuthorName(), savedAuthor.getAuthorName())
        );
    }

}
