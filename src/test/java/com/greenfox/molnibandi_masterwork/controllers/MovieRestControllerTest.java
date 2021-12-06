package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;
import com.greenfox.molnibandi_masterwork.models.dtos.MovieDTO;
import com.greenfox.molnibandi_masterwork.services.DefaultFilmStudioService;
import com.greenfox.molnibandi_masterwork.services.DefaultMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(MovieRestController.class)
class MovieRestControllerTest {

    @Autowired
    private MovieRestController controller;
    @MockBean
    private DefaultMovieService service;

    private MovieDTO expectedMovie1;
    private MovieDTO expectedMovie2;
    private List<MovieDTO> expected;
    private Set<String> actorsName = new HashSet<>();

    @BeforeEach
    void init() {
        expectedMovie1 =
                new MovieDTO(1L, "Star Wars - A new Hope", "https://www.imdb.com/title/tt0076759/mediaviewer/rm3263717120/", 1977, BigDecimal.valueOf(8.6),
                        "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empires world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.",
                        BigDecimal.valueOf(775.4), "fantasy", "Lucasfilm", "George Lucas", actorsName);
        expectedMovie2 =
                new MovieDTO(2L, "Oscar", "https://www.imdb.com/title/tt0102603/mediaviewer/rm3571070464/", 1991, BigDecimal.valueOf(6.5), "A gangster attempts to keep the promise he made to his dying father: that he would give up his life of crime and \"go straight\".", BigDecimal.valueOf(23.6), "comedy", "Touchstone Pictures", "John Landis", actorsName);
        expected = Arrays.asList(expectedMovie1, expectedMovie2);
    }

    @Test
    public void listMovies_ReturnsAllMovies_ReturnsResponseEntityOk() {
        Mockito.when(service.listAllMovies())
                .thenReturn(expected);

        ResponseEntity<List<MovieDTO>> response = controller.listMovies();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void listMovies_ReturnsAllMovies_ReturnedDataIsCorrect() {
        Mockito.when(service.listAllMovies()).thenReturn(expected);

        ResponseEntity<List<MovieDTO>> response = controller.listMovies();
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Oscar", response.getBody().get(1).getTitle());
    }

    @Test
    public void createMovie_ValidParameterIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.addMovie(expectedMovie1)).thenReturn(expectedMovie1);

        ResponseEntity<MovieDTO> response = controller.createMovie(expectedMovie1, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedMovie1, response.getBody());
    }

    @Test
    public void createMovie_NullParameterIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.createMovie(null, null));
    }

    @Test
    public void getMovieById_ValidDataIsAssignedToId_ReturnsResponseEntityOk() {
        Mockito.when(service.findMovieById(1L)).thenReturn(expectedMovie1);

        ResponseEntity<MovieDTO> response = controller.getMovieById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedMovie1, response.getBody());
    }

    @Test
    public void getMovieById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.getMovieById(null));
    }

    @Test
    public void deleteMovieById_ValidIdIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.removeMovie(1L)).thenReturn(expectedMovie1);

        ResponseEntity<MovieDTO> response = controller.deleteMovieById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedMovie1, response.getBody());
    }

    @Test
    public void deleteMovieById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.deleteMovieById(null));
    }

    @Test
    public void updateMovie_ValidParametersAreGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.modifyMovie(expectedMovie1, 1L)).thenReturn(expectedMovie2);

        ResponseEntity<MovieDTO> response = controller.updateMovie("1", expectedMovie2, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void updateMovie_NullParametersAreGiven_IllegalArgumentExceptionIsThrown() {
        Mockito.when(service.modifyMovie(expectedMovie2, 1L)).thenReturn(expectedMovie2);

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateMovie(null, expectedMovie2, null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateMovie("1", null, null));
    }

}
