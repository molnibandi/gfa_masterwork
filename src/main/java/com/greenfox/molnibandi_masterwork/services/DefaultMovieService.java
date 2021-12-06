package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.MovieDTO;
import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import com.greenfox.molnibandi_masterwork.models.entities.Director;
import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import com.greenfox.molnibandi_masterwork.models.entities.Movie;
import com.greenfox.molnibandi_masterwork.repositories.ActorRepository;
import com.greenfox.molnibandi_masterwork.repositories.DirectorRepository;
import com.greenfox.molnibandi_masterwork.repositories.FilmStudioRepository;
import com.greenfox.molnibandi_masterwork.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNonNull;
import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNull;

@Service
@Transactional
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final FilmStudioRepository filmStudioRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public DefaultMovieService(
            MovieRepository movieRepository, DirectorRepository directorRepository, FilmStudioRepository filmStudioRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.filmStudioRepository = filmStudioRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public MovieDTO findMovieById(Long id) {
        requireNonNull(id, "Id must not be null!");

        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Movie with ID: " + id + " cannot be found."));

        return MovieDTO.convertToMovieDTO(movie);
    }

    @Override
    public List<MovieDTO> listAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieDTO::convertToMovieDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO addMovie(MovieDTO movie) {
        requireNonNull(movie, "Movie must not be null!");
        requireNull(movie.getId(), "Movie ID must be null!");

        Set<Actor> actors = getActors(movie);

        if (!directorRepository.existsDirectorByName(movie.getDirectorName())) {
            throw new EntityNotFoundException(
                    "Director: " + movie.getDirectorName() + " not found, please add first");
        }

        if (!filmStudioRepository.existsFilmStudioByName(movie.getFilmStudioName())) {
            throw new EntityNotFoundException(
                    "Film studio: " + movie.getFilmStudioName() + " not found, please add first");
        }

        if (movieRepository.existsMovieByTitle(movie.getTitle())) {
            throw new IllegalArgumentException("Title must be unique.");
        }

        Director director = directorRepository.findDirectorByName(movie.getDirectorName());
        FilmStudio filmStudio = filmStudioRepository.findFilmStudioByName(movie.getFilmStudioName());

        return MovieDTO.convertToMovieDTO(
                movieRepository.save(movie.convertToMovie(filmStudio, director, actors)));
    }

    @Override
    public MovieDTO removeMovie(Long id) {
        requireNonNull(id, "Id must not be null!");

        Movie movie = movieRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Movie with ID: " + id + " cannot be found."));

        movieRepository.delete(movie);

        return MovieDTO.convertToMovieDTO(movie);
    }

    @Override
    public MovieDTO modifyMovie(MovieDTO movie, Long idOfOrigin) {
        requireNonNull(movie, "Movie must not be null!");
        requireNonNull(idOfOrigin, "ID of original movie must not be null!");

        Movie originalMovie = movieRepository.findById(idOfOrigin).orElseThrow(
                () -> new EntityNotFoundException(
                        "Movie with ID: " + idOfOrigin + " cannot be found."));

        if (!originalMovie.getId().equals(movie.getId())) {
            if (movieRepository.existsById(movie.getId())) {
                throw new IllegalArgumentException("Identification number must be unique.");
            }
        }

        if (!directorRepository.existsDirectorByName(movie.getDirectorName())) {
            throw new EntityNotFoundException(
                    "Director: " + movie.getDirectorName() + " not found, please add first");
        }

        if (!filmStudioRepository.existsFilmStudioByName(movie.getFilmStudioName())) {
            throw new EntityNotFoundException(
                    "Film studio: " + movie.getFilmStudioName() + " not found, please add first");
        }

        if (movieRepository.existsMovieByTitle(movie.getTitle()) &&
                !movie.getTitle().equals(originalMovie.getTitle())) {
            throw new IllegalArgumentException("Title must be unique.");
        }

        movie.setId(idOfOrigin);

        Director director = directorRepository.findDirectorByName(movie.getDirectorName());
        FilmStudio filmStudio = filmStudioRepository.findFilmStudioByName(movie.getFilmStudioName());

        movieRepository.save(movie.convertToMovie(filmStudio, director, getActors(movie)));

        return movie;
    }

    private Set<Actor> getActors(MovieDTO movie) {
        Set<String> names = movie.getActorsName();
        Set<Actor> actors = actorRepository.findAll().stream()
                .filter(actor -> names.contains(actor.getName()))
                .collect(Collectors.toSet());

        if (actors.size() != names.size()) {
            Set<String> foundNames = actors.stream().map(Actor::getName).collect(Collectors.toSet());
            names.removeAll(foundNames);
            throw new EntityNotFoundException(
                    "Could not find the following actor(s) in the database: " + names
                            + " add them before assigning/casting them to a movie.");
        }

        return actors;
    }

}
