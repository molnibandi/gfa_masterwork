package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.DirectorDTO;
import com.greenfox.molnibandi_masterwork.models.entities.Director;
import com.greenfox.molnibandi_masterwork.repositories.DirectorRepository;
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
public class DefaultDirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DefaultDirectorService service;

    private List<DirectorDTO> directors;

    @BeforeEach
    void setUp() {
        directors = Arrays.asList(
                new DirectorDTO(1L, "Test1", "Test1"),
                new DirectorDTO(2L, "Test2", "Test2"),
                new DirectorDTO(3L, "Test3", "Test3"),
                new DirectorDTO(4L, "Test4", "Test4"));
    }

    @Test
    void findDirectorById_WorksCorrectly() {
        int id = 2;
        Mockito.when(directorRepository.findById((long) id))
                .thenReturn(Optional.of(directors.get(id - 1).convertToDirector()));

        DirectorDTO actual = service.findDirectorById((long) id);

        assertEquals(directors.get(id - 1), actual);
    }

    @Test
    void findDirectorById_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findDirectorById(null));
    }

    @Test
    void findDirectorById_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findDirectorById(100L));
    }

    @Test
    void listAllDirectors_WorksCorrectly() {
        Mockito.when(directorRepository.findAll()).thenReturn(
                directors.stream().map(DirectorDTO::convertToDirector).collect(Collectors.toList()));

        List<DirectorDTO> actual = service.listAllDirectors();

        assertEquals(directors, actual);
    }

    @Test
    void addDirector_WorksCorrectly() {
        DirectorDTO objectToAdd = new DirectorDTO(5L, "Test5", "Test");
        Mockito.when(directorRepository.save(any(Director.class))).thenReturn(objectToAdd.convertToDirector());

        objectToAdd.setId(null);

        DirectorDTO actual = service.addDirector(objectToAdd);

        objectToAdd.setId(5L);

        assertEquals(objectToAdd, actual);
    }

    @Test
    void addDirector_ThrowsIllegalArg_WhenDirectorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.addDirector(null));
    }

    @Test
    void addDirector_ThrowsIllegalArg_WhenDirectorHasId() {
        assertThrows(IllegalArgumentException.class, () -> service.addDirector(directors.get(0)));
    }

    @Test
    void removeDirector_WorksCorrectly() {
        int id = 2;

        Mockito.when(directorRepository.findById((long) id))
                .thenReturn(Optional.of(directors.get(id - 1).convertToDirector()));

        DirectorDTO actual = service.removeDirector((long) id);

        assertEquals(directors.get(id - 1), actual);
    }

    @Test
    void removeDirector_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeDirector(null));
    }

    @Test
    void removeDirector_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.removeDirector(100L));
    }

    @Test
    void modifyDirector_WorksCorrectly() {
        int id = 2;
        DirectorDTO dataObject = new DirectorDTO(10L, "mod", "mod");

        DirectorDTO expected = new DirectorDTO(2L, "mod", "mod");

        Mockito.when(directorRepository.findById((long) id))
                .thenReturn(Optional.of(directors.get(1).convertToDirector()));

        DirectorDTO actual = service.modifyDirector(dataObject, (long) id);

        assertEquals(expected, actual);
    }

    @Test
    void modifyDirector_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyDirector(directors.get(0), null));
    }

    @Test
    void modifyDirector_ThrowsIllegalArg_WhenDirectorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyDirector(null, 1L));
    }

    @Test
    void modifyDirector_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.modifyDirector(directors.get(0), 100L));
    }

}
