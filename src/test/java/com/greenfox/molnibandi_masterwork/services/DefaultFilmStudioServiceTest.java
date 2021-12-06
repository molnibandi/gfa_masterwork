package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;
import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import com.greenfox.molnibandi_masterwork.repositories.FilmStudioRepository;
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
public class DefaultFilmStudioServiceTest {

    @Mock
    private FilmStudioRepository filmStudioRepository;

    @InjectMocks
    private DefaultFilmStudioService service;

    private List<FilmStudioDTO> filmStudios;

    @BeforeEach
    void setUp() {
        filmStudios = Arrays.asList(
                new FilmStudioDTO(1L, "Test1", 2000, "Test"),
                new FilmStudioDTO(2L, "Test2", 2000, "Test"),
                new FilmStudioDTO(3L, "Test3", 2000, "Test"),
                new FilmStudioDTO(4L, "Test4", 2000, "Test"));
    }

    @Test
    void findFilmStudioById_WorksCorrectly() {
        int id = 2;
        Mockito.when(filmStudioRepository.findById((long) id))
                .thenReturn(Optional.of(filmStudios.get(id - 1).convertToFilmStudio()));

        FilmStudioDTO actual = service.findFilmStudioById((long) id);

        assertEquals(filmStudios.get(id - 1), actual);
    }

    @Test
    void findFilmStudioById_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findFilmStudioById(null));
    }

    @Test
    void findFilmStudioById_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findFilmStudioById(100L));
    }

    @Test
    void listAllFilmStudios_WorksCorrectly() {
        Mockito.when(filmStudioRepository.findAll()).thenReturn(
                filmStudios.stream().map(FilmStudioDTO::convertToFilmStudio).collect(Collectors.toList()));

        List<FilmStudioDTO> actual = service.listAllFilmStudios();

        assertEquals(filmStudios, actual);
    }

    @Test
    void addFilmStudio_WorksCorrectly() {
        FilmStudioDTO objectToAdd = new FilmStudioDTO(5L, "Test5", 2000, "Test");
        Mockito.when(filmStudioRepository.save(any(FilmStudio.class))).thenReturn(objectToAdd.convertToFilmStudio());

        objectToAdd.setId(null);

        FilmStudioDTO actual = service.addFilmStudio(objectToAdd);

        objectToAdd.setId(5L);

        assertEquals(objectToAdd, actual);
    }

    @Test
    void addFilmStudio_ThrowsIllegalArg_WhenFilmStudioIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.addFilmStudio(null));
    }

    @Test
    void addFilmStudio_ThrowsIllegalArg_WhenFilmStudioHasId() {
        assertThrows(IllegalArgumentException.class, () -> service.addFilmStudio(filmStudios.get(0)));
    }

    @Test
    void removeFilmStudio_WorksCorrectly() {
        int id = 2;

        Mockito.when(filmStudioRepository.findById((long) id))
                .thenReturn(Optional.of(filmStudios.get(id - 1).convertToFilmStudio()));

        FilmStudioDTO actual = service.removeFilmStudio((long) id);

        assertEquals(filmStudios.get(id - 1), actual);
    }

    @Test
    void removeFilmStudio_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeFilmStudio(null));
    }

    @Test
    void removeFilmStudio_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.removeFilmStudio(100L));
    }

    @Test
    void modifyFilmStudio_WorksCorrectly() {
        int id = 2;
        FilmStudioDTO dataObject = new FilmStudioDTO(10L, "mod", 2000, "Test");

        FilmStudioDTO expected = new FilmStudioDTO(2L, "mod", 2000, "Test");

        Mockito.when(filmStudioRepository.findById((long) id))
                .thenReturn(Optional.of(filmStudios.get(1).convertToFilmStudio()));

        FilmStudioDTO actual = service.modifyFilmStudio(dataObject, (long) id);

        assertEquals(expected, actual);
    }

    @Test
    void modifyFilmStudio_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyFilmStudio(filmStudios.get(0), null));
    }

    @Test
    void modifyFilmStudio_ThrowsIllegalArg_WhenFilmStudioIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyFilmStudio(null, 1L));
    }

    @Test
    void modifyFilmStudio_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.modifyFilmStudio(filmStudios.get(0), 100L));
    }

}
