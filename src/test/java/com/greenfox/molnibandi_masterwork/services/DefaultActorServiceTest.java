package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;
import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import com.greenfox.molnibandi_masterwork.repositories.ActorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DefaultActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private DefaultActorService service;

    private List<ActorDTO> actors;

    @BeforeEach
    void setUp() {
        actors = Arrays.asList(
                new ActorDTO(1L, "Test1", "Test1"),
                new ActorDTO(2L, "Test2", "Test2"),
                new ActorDTO(3L, "Test3", "Test3"),
                new ActorDTO(4L, "Test4", "Test4"));
    }

    @Test
    void findActorById_WorksCorrectly() {
        int id = 2;
        Mockito.when(actorRepository.findById((long) id))
                .thenReturn(Optional.of(actors.get(id - 1).convertToActor()));

        ActorDTO actual = service.findActorById((long) id);

        assertEquals(actors.get(id - 1), actual);
    }

    @Test
    void findActorById_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findActorById(null));
    }

    @Test
    void findActorById_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findActorById(100L));
    }

    @Test
    void listAllActors_WorksCorrectly() {
        Mockito.when(actorRepository.findAll()).thenReturn(
                actors.stream().map(ActorDTO::convertToActor).collect(Collectors.toList()));

        List<ActorDTO> actual = service.listAllActors();

        assertEquals(actors, actual);
    }

    @Test
    void addActor_WorksCorrectly() {
        ActorDTO objectToAdd = new ActorDTO(5L, "Test5", "Test");
        Mockito.when(actorRepository.save(any(Actor.class))).thenReturn(objectToAdd.convertToActor());

        objectToAdd.setId(null);

        ActorDTO actual = service.addActor(objectToAdd);

        objectToAdd.setId(5L);

        assertEquals(objectToAdd, actual);
    }

    @Test
    void addActor_ThrowsIllegalArg_WhenActorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.addActor(null));
    }

    @Test
    void addActor_ThrowsIllegalArg_WhenActorHasId() {
        assertThrows(IllegalArgumentException.class, () -> service.addActor(actors.get(0)));
    }

    @Test
    void removeActor_WorksCorrectly() {
        int id = 2;

        Mockito.when(actorRepository.findById((long) id))
                .thenReturn(Optional.of(actors.get(id - 1).convertToActor()));

        ActorDTO actual = service.removeActor((long) id);

        assertEquals(actors.get(id - 1), actual);
    }

    @Test
    void removeActor_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeActor(null));
    }

    @Test
    void removeActor_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.removeActor(100L));
    }

    @Test
    void modifyActor_WorksCorrectly() {
        int id = 2;
        ActorDTO dataObject = new ActorDTO(10L, "mod", "mod");

        ActorDTO expected = new ActorDTO(2L, "mod", "mod");

        Mockito.when(actorRepository.findById((long) id))
                .thenReturn(Optional.of(actors.get(1).convertToActor()));

        ActorDTO actual = service.modifyActor(dataObject, (long) id);

        assertEquals(expected, actual);
    }

    @Test
    void modifyActor_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyActor(actors.get(0), null));
    }

    @Test
    void modifyActor_ThrowsIllegalArg_WhenActorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyActor(null, 1L));
    }

    @Test
    void modifyActor_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.modifyActor(actors.get(0), 100L));
    }

}
