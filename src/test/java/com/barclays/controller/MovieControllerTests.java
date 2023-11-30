package com.barclays.controller;
import com.barclays.model.Book;
import com.barclays.model.Movie;
import com.barclays.service.MovieService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
public class MovieControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    @BeforeEach
    public void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Test
    public void testGettingAllMovies() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Movie[] movies = mapper.readValue(contentAsString, Movie[].class);

        int expectedLength = 3;
        assertAll("Testing get all movies",
                () -> assertEquals(expectedLength, movies.length),
                () -> assertNotNull(movies[0].getTitle()));
    }

    @Test
    public void testCreateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDirector("Test Director");
        movie.setGenre("Test Genre");

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(movie)))
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        movie = mapper.readValue(contentAsString, Movie.class);

        assertEquals("Test Movie", movie.getTitle());
    }


    @Test
    public void testUpdateMovie() throws Exception {
        Movie movieToUpdate = new Movie();
        movieToUpdate.setTitle("Updated Title");
        movieToUpdate.setDirector("Updated Director");
        movieToUpdate.setGenre("Updated Genre");

        int movieIdToUpdate = 500;

        mockMvc.perform(MockMvcRequestBuilders.put("/movies/" + movieIdToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(movieToUpdate))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + movieIdToUpdate)
                    .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String updatedContent = result.getResponse().getContentAsString();
        Movie updatedMovie = mapper.readValue(updatedContent, Movie.class);

        assertAll("Check updated movie fields",
                () -> assertEquals("Updated Title", updatedMovie.getTitle()),
                () -> assertEquals("Updated Director", updatedMovie.getDirector()),
                () -> assertEquals("Updated Genre", updatedMovie.getGenre()));
    }


    @Test
    public void testDeleteMovie() throws Exception {

        int movieIdToDelete = 500;

        mockMvc.perform(MockMvcRequestBuilders.delete("/movies/"+movieIdToDelete))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/" + movieIdToDelete)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
