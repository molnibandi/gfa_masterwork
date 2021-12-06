package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;
import com.greenfox.molnibandi_masterwork.services.FilmStudioService;
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
@RequestMapping("/api/moviedb-app/filmstudios")
public class FilmStudioRestController {

    private final FilmStudioService filmStudioService;

    @Autowired
    public FilmStudioRestController(
            FilmStudioService filmStudioService) {
        this.filmStudioService = filmStudioService;
    }

    @GetMapping()
    public ResponseEntity<List<FilmStudioDTO>> listFilmStudios() {
        return ResponseEntity.ok(filmStudioService.listAllFilmStudios());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a film studio with the given ID")
    public ResponseEntity<FilmStudioDTO> getFilmStudioById(
            @ApiParam(value = "ID of the queried film studio", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        FilmStudioDTO found = filmStudioService.findFilmStudioById(isNumeric(id));
        return ResponseEntity.ok(found);
    }

    @PostMapping()
    @ApiOperation(value = "Creates a new film studio")
    public ResponseEntity<FilmStudioDTO> createFilmStudio(
            @ApiParam(value = "The film studio to be created", required = true)
            @RequestBody(required = false) @Valid FilmStudioDTO filmStudio,
            BindingResult bindingResult) {

        requireNonNull(filmStudio, "Required fields in the body: (Str) name, (int) founded, (Str) address");
        checkBinding(bindingResult);

        FilmStudioDTO createdFilmStudio = filmStudioService.addFilmStudio(filmStudio);
        URI location = URI.create(String.format("/api/moviedb-app/filmstudios/%s", createdFilmStudio.getId()));
        return ResponseEntity.created(location).body(createdFilmStudio);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a film studio")
    public ResponseEntity<FilmStudioDTO> deleteFilmStudioById(
            @ApiParam(value = "ID of the film studio to be deleted", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        FilmStudioDTO deleted = filmStudioService.removeFilmStudio(isNumeric(id));
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an existing film studio")
    public ResponseEntity<FilmStudioDTO> updateFilmStudio(
            @ApiParam(value = "ID of the film studio to be updated", required = true, type = "Long")
            @PathVariable String id,

            @ApiParam(value = "The updated film studio to be saved", required = true)
            @RequestBody(required = false) @Valid FilmStudioDTO filmStudio,
            BindingResult bindingResult) {

        requireNonNull(id, "Id must not be null.");
        requireNonNull(filmStudio, "Required fields in the body: (Str) name, (int) founded, (Str) address");
        checkBinding(bindingResult);

        filmStudioService.modifyFilmStudio(filmStudio, isNumeric(id));
        return ResponseEntity.noContent().build();
    }

}
