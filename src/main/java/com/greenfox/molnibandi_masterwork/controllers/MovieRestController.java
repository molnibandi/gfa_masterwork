package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.MovieDTO;
import com.greenfox.molnibandi_masterwork.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.greenfox.molnibandi_masterwork.services.ValidationService.*;

@RestController
@RequestMapping("/api/moviedb-app/movies")
public class MovieRestController {

    private final MovieService movieService;

    @Autowired
    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    @ApiOperation(value = "Lists all movies")
    public ResponseEntity<List<MovieDTO>> listMovies() {
        return ResponseEntity.ok(movieService.listAllMovies());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a movie with the given ID")
    public ResponseEntity<MovieDTO> getMovieById(
            @ApiParam(value = "ID of the queried movie", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        MovieDTO found = movieService.findMovieById(isNumeric(id));
        return ResponseEntity.ok(found);
    }

    @PostMapping()
    @ApiOperation(value = "Creates a new movie")
    public ResponseEntity<MovieDTO> createMovie(
            @ApiParam(value = "The movie to be created", required = true)
            @RequestBody(required = false) @Valid MovieDTO movie,
            BindingResult bindingResult) {

        requireNonNull(movie, "Check the required fields in the body");
        checkBinding(bindingResult);

        MovieDTO createdMovie = movieService.addMovie(movie);
        URI location = URI.create(String.format("/api/moviedb-app/movies/%s", createdMovie.getId()));
        return ResponseEntity.created(location).body(createdMovie);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a movie")
    public ResponseEntity<MovieDTO> deleteMovieById(
            @ApiParam(value = "ID of the movie to be deleted", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        MovieDTO deleted = movieService.removeMovie(isNumeric(id));
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an existing movie")
    public ResponseEntity<MovieDTO> updateMovie(
            @ApiParam(value = "ID of the movie to be updated", required = true, type = "Long")
            @PathVariable String id,

            @ApiParam(value = "The updated movie to be saved", required = true)
            @RequestBody(required = false) @Valid MovieDTO movie,
            BindingResult bindingResult) {

        requireNonNull(id, "Id must not be null.");
        requireNonNull(movie, "Check the required fields in the body.");
        checkBinding(bindingResult);

        movieService.modifyMovie(movie, isNumeric(id));
        return ResponseEntity.noContent().build();
    }

}
