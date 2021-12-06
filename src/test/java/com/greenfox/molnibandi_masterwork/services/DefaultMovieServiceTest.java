package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.MovieDTO;
import com.greenfox.molnibandi_masterwork.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DefaultMovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private DefaultMovieService service;

    private List<MovieDTO> movies;
    private final Set<String> actorsName = new HashSet<>();

    @BeforeEach
    void setUp() {
        movies = Arrays.asList(
                new MovieDTO(1L, "Test1", "Test", 2000, BigDecimal.valueOf(8.5), "Test", BigDecimal.valueOf(100.0), "Test", "Test", "Test", actorsName),
                new MovieDTO(2L, "Test2", "Test", 2000, BigDecimal.valueOf(8.5), "Test", BigDecimal.valueOf(100.0), "Test", "Test", "Test", actorsName),
                new MovieDTO(3L, "Test3", "Test", 2000, BigDecimal.valueOf(8.5), "Test", BigDecimal.valueOf(100.0), "Test", "Test", "Test", actorsName),
                new MovieDTO(4L, "Test4", "Test", 2000, BigDecimal.valueOf(8.5), "Test", BigDecimal.valueOf(100.0), "Test", "Test", "Test", actorsName));
    }

    @Test
    void findMovieById_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findMovieById(null));
    }

    @Test
    void findMovieById_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.findMovieById(100L));
    }

    @Test
    void addMovie_ThrowsIllegalArg_WhenMovieIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.addMovie(null));
    }

    @Test
    void addMovie_ThrowsIllegalArg_WhenMovieHasId() {
        assertThrows(IllegalArgumentException.class, () -> service.addMovie(movies.get(0)));
    }

    @Test
    void removeMovie_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.removeMovie(null));
    }

    @Test
    void removeMovie_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.removeMovie(100L));
    }

    @Test
    void modifyMovie_ThrowsIllegalArg_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyMovie(movies.get(0), null));
    }

    @Test
    void modifyMovie_ThrowsIllegalArg_WhenActorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.modifyMovie(null, 1L));
    }

    @Test
    void modifyMovie_ThrowsEntityNotFound_WhenIdIsNotFound() {
        assertThrows(EntityNotFoundException.class, () -> service.modifyMovie(movies.get(0), 100L));
    }

}
