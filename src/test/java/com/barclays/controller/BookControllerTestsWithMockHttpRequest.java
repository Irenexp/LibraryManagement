package com.barclays.controller;

import com.barclays.model.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.barclays.model.Book;
import com.barclays.service.BookService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})

public class BookControllerTestsWithMockHttpRequest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Test
    public void testGettingAllBooks() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book[] books = mapper.readValue(contentAsString, Book[].class);

        int expectedLength = 6;
        assertAll("Testing get all books",
                () -> assertEquals(expectedLength, books.length),
                () -> assertNotNull(books[0].getTitle()));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book();
        Author author = new Author("Test Author");
        book.setTitle("Test Book");
        book.setPublisher("Test Publisher");
        book.setGenre("Test Genre");
        book.setAuthor(author);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        book = mapper.readValue(contentAsString, Book.class);

        assertEquals("Test Book", book.getTitle());
    }

    @Test
    public void testUpdateBook() throws Exception {

        Book bookToUpdate = new Book();
        bookToUpdate.setTitle("Updated Title");
        bookToUpdate.setPublisher("Updated Publisher");
        bookToUpdate.setGenre("Updated Genre");

        int bookIdToUpdate = 100;

        mockMvc.perform(MockMvcRequestBuilders.put("/books/" + bookIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookToUpdate))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookIdToUpdate)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String updatedContent = result.getResponse().getContentAsString();
        Book updatedBook = mapper.readValue(updatedContent, Book.class);

        assertAll("Check updated book fields",
                () -> assertEquals("Updated Title", updatedBook.getTitle()),
                () -> assertEquals("Updated Publisher", updatedBook.getPublisher()),
                () -> assertEquals("Updated Genre", updatedBook.getGenre()));
    }

    @Test
    public void testDeleteNotBorrowedBook() throws Exception {

        Book bookToDelete = new Book();
        bookToDelete.setId(600);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookToDelete)))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + bookToDelete.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testDeleteBorrowedBook() throws Exception {
//
//        Book bookToDelete = new Book();
//        bookToDelete.setId(400);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/books")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.content().string("Cannot delete book as it has borrowed records."));
//
//    }

    @Test
    public void testFindBookByGenre() throws Exception {
        String genre = "Fantasy";
        int expectedNumberOfBooks = 5;

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/books/searchByGenre")
                        .param("genre", genre)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Book[] books = mapper.readValue(contentAsString, Book[].class);

        assertEquals(expectedNumberOfBooks, books.length);

    }
}


