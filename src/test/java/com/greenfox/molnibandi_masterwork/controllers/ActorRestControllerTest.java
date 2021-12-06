package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;
import com.greenfox.molnibandi_masterwork.services.DefaultActorService;
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

@WebMvcTest(ActorRestController.class)
class ActorRestControllerTest {

    @Autowired
    private ActorRestController controller;
    @MockBean
    private DefaultActorService service;

    private ActorDTO expectedActor1;
    private ActorDTO expectedActor2;
    private List<ActorDTO> expected;

    @BeforeEach
    void init() {
        expectedActor1 =
                new ActorDTO(1L, "Bruce Willis", "Actor and musician Bruce Willis is well known for playing wisecracking or hard-edged characters, often in spectacular action films.");
        expectedActor2 =
                new ActorDTO(2L, "Carrie Fisher",
                        "Carrie Frances Fisher was born on October 21, 1956 in Burbank, California, to singers/actors Eddie Fisher and Debbie Reynolds. She was an actress and writer.");
        expected = Arrays.asList(expectedActor1, expectedActor2);
    }

    @Test
    public void listActors_ReturnsAllActors_ReturnsResponseEntityOk() {
        Mockito.when(service.listAllActors())
                .thenReturn(expected);

        ResponseEntity<List<ActorDTO>> response = controller.listActors();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void listActors_ReturnsAllActors_ReturnedDataIsCorrect() {
        Mockito.when(service.listAllActors()).thenReturn(expected);

        ResponseEntity<List<ActorDTO>> response = controller.listActors();
        assertTrue(response.hasBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Carrie Fisher", response.getBody().get(1).getName());
    }

    @Test
    public void createActor_ValidParameterIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.addActor(expectedActor1)).thenReturn(expectedActor1);

        ResponseEntity<ActorDTO> response = controller.createActor(expectedActor1, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedActor1, response.getBody());
    }

    @Test
    public void createActor_NullParameterIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.createActor(null, null));
    }

    @Test
    public void getActorById_ValidDataIsAssignedToId_ReturnsResponseEntityOk() {
        Mockito.when(service.findActorById(1L)).thenReturn(expectedActor1);

        ResponseEntity<ActorDTO> response = controller.getActorById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedActor1, response.getBody());
    }

    @Test
    public void getActorById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.getActorById(null));
    }

    @Test
    public void deleteActorById_ValidIdIsGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.removeActor(1L)).thenReturn(expectedActor1);

        ResponseEntity<ActorDTO> response = controller.deleteActorById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(expectedActor1, response.getBody());
    }

    @Test
    public void deleteActorById_NullIdIsGiven_IllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> controller.deleteActorById(null));
    }

    @Test
    public void updateActor_ValidParametersAreGiven_ReturnsResponseEntityOk() {
        Mockito.when(service.modifyActor(expectedActor2, 1L)).thenReturn(expectedActor2);

        ResponseEntity<ActorDTO> response = controller.updateActor("1", expectedActor2, null);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(response.hasBody());
    }

    @Test
    public void updateActor_NullParametersAreGiven_IllegalArgumentExceptionIsThrown() {
        Mockito.when(service.modifyActor(expectedActor2, 1L)).thenReturn(expectedActor2);

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateActor(null, expectedActor2, null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateActor("1", null, null));
    }

}
