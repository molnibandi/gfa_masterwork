package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;
import com.greenfox.molnibandi_masterwork.services.DefaultFilmStudioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(FilmStudioRestController.class)
class FilmStudioRestControllerTest {

    @Autowired
    private FilmStudioRestController controller;
    @MockBean
    private DefaultFilmStudioService service;

    private FilmStudioDTO expectedFilmStudio1;
    private FilmStudioDTO expectedFilmStudio2;
    private List<FilmStudioDTO> expected;

    @BeforeEach
    void init() {
        expectedFilmStudio1 =
                new FilmStudioDTO(1L, "Warner Bros. Pictures", 1923, "Hollywood, Los Angeles, California, USA");
        expectedFilmStudio2 =
                new FilmStudioDTO(2L, "New Line Cinema", 1967, "New York, New York, USA");
        expected = Arrays.asList(expectedFilmStudio1, expectedFilmStudio2);
    }

    @Test
    public void listFilmStudios_ReturnsAllStudios_ReturnsResponseEntityOk() {
        Mockito.when(service.listAllFilmStudios())
                .thenReturn(expected);

        ResponseEntity<List<FilmStudioDTO>> response = controller.listFilmStudios();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void listFilmStudios_ReturnsAllStudios_ReturnedDataIsCorrect() {
        Mockito.when(service.listAllFilmStudios()).thenReturn(expected);

        ResponseEntity<List<FilmStudioDTO>> response = controller.listFilmStudios();
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("New Line Cinema", response.getBody().get(1).getName());
    }

    @Test
    public void createFilmStudio_ValidParameterIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.addFilmStudio(expectedFilmStudio1)).thenReturn(expectedFilmStudio1);

        ResponseEntity<FilmStudioDTO> response = controller.createFilmStudio(expectedFilmStudio1, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedFilmStudio1, response.getBody());
    }

    @Test
    public void createFilmStudio_NullParameterIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.createFilmStudio(null, null));
    }

    @Test
    public void getFilmStudioById_ValidDataIsAssignedToId_ReturnsResponseEntityOk() {
        Mockito.when(service.findFilmStudioById(1L)).thenReturn(expectedFilmStudio1);

        ResponseEntity<FilmStudioDTO> response = controller.getFilmStudioById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedFilmStudio1, response.getBody());
    }

    @Test
    public void getFilmStudioById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.getFilmStudioById(null));
    }

    @Test
    public void deleteFilmStudioById_ValidIdIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.removeFilmStudio(1L)).thenReturn(expectedFilmStudio1);

        ResponseEntity<FilmStudioDTO> response = controller.deleteFilmStudioById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedFilmStudio1, response.getBody());
    }

    @Test
    public void deleteFilmStudioById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.deleteFilmStudioById(null));
    }

    @Test
    public void updateFilmStudio_ValidParametersAreGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.modifyFilmStudio(expectedFilmStudio2, 1L)).thenReturn(expectedFilmStudio2);

        ResponseEntity<FilmStudioDTO> response = controller.updateFilmStudio("1", expectedFilmStudio2, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void updateFilmStudio_NullParametersAreGiven_IllegalArgumentExceptionIsThrown() {
        Mockito.when(service.modifyFilmStudio(expectedFilmStudio2, 1L)).thenReturn(expectedFilmStudio2);

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateFilmStudio(null, expectedFilmStudio2, null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateFilmStudio("1", null, null));
    }

}
