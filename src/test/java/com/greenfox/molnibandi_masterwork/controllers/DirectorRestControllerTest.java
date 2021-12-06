package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.DirectorDTO;
import com.greenfox.molnibandi_masterwork.services.DefaultDirectorService;
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

@WebMvcTest(DirectorRestController.class)
class DirectorRestControllerTest {

    @Autowired
    private DirectorRestController controller;
    @MockBean
    private DefaultDirectorService service;

    private DirectorDTO expectedDirector1;
    private DirectorDTO expectedDirector2;
    private List<DirectorDTO> expected;

    @BeforeEach
    void init() {
        expectedDirector1 =
                new DirectorDTO(1L, "George Lucas",
                        "George Walton Lucas, Jr. was raised on a walnut ranch in Modesto, California. His father was a stationery store owner and he had three siblings.");
        expectedDirector2 =
                new DirectorDTO(2L, "John Landis",
                        "John Landis began his career in the mail room of 20th Century-Fox. A high-school dropout, 18-year-old Landis made his way to Yugoslavia to work as a production assistant.");
        expected = Arrays.asList(expectedDirector1, expectedDirector2);
    }

    @Test
    public void listDirectors_ReturnsAllDirectors_ReturnsResponseEntityOk() {
        Mockito.when(service.listAllDirectors())
                .thenReturn(expected);

        ResponseEntity<List<DirectorDTO>> response = controller.listDirectors();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void listDirectors_ReturnsAllDirectors_ReturnedDataIsCorrect() {
        Mockito.when(service.listAllDirectors()).thenReturn(expected);

        ResponseEntity<List<DirectorDTO>> response = controller.listDirectors();
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("John Landis", response.getBody().get(1).getName());
    }

    @Test
    public void createDirector_ValidParameterIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.addDirector(expectedDirector1)).thenReturn(expectedDirector1);

        ResponseEntity<DirectorDTO> response = controller.createDirector(expectedDirector1, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedDirector1, response.getBody());
    }

    @Test
    public void createDirector_NullParameterIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.createDirector(null, null));
    }

    @Test
    public void getDirectorById_ValidDataIsAssignedToId_ReturnsResponseEntityOk() {
        Mockito.when(service.findDirectorById(1L)).thenReturn(expectedDirector1);

        ResponseEntity<DirectorDTO> response = controller.getDirectorById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedDirector1, response.getBody());
    }

    @Test
    public void getDirectorById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.getDirectorById(null));
    }

    @Test
    public void deleteDirectorById_ValidIdIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.removeDirector(1L)).thenReturn(expectedDirector1);

        ResponseEntity<DirectorDTO> response = controller.deleteDirectorById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedDirector1, response.getBody());
    }

    @Test
    public void deleteDirectorById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.deleteDirectorById(null));
    }

    @Test
    public void updateDirector_ValidParametersAreGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.modifyDirector(expectedDirector2, 1L)).thenReturn(expectedDirector2);

        ResponseEntity<DirectorDTO> response = controller.updateDirector("1", expectedDirector2, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void updateDirector_NullParametersAreGiven_IllegalArgumentExceptionIsThrown() {
        Mockito.when(service.modifyDirector(expectedDirector2, 1L)).thenReturn(expectedDirector2);

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateDirector(null, expectedDirector2, null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateDirector("1", null, null));
    }

}
