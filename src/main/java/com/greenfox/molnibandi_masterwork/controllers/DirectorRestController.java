package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.DirectorDTO;
import com.greenfox.molnibandi_masterwork.services.DirectorService;
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
@RequestMapping("/api/moviedb-app/directors")
public class DirectorRestController {

    private final DirectorService directorService;

    @Autowired
    public DirectorRestController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping()
    @ApiOperation(value = "Lists all directors")
    public ResponseEntity<List<DirectorDTO>> listDirectors() {
        return ResponseEntity.ok(directorService.listAllDirectors());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a director with the given ID")
    public ResponseEntity<DirectorDTO> getDirectorById(
            @ApiParam(value = "ID of the queried director", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        DirectorDTO found = directorService.findDirectorById(isNumeric(id));
        return ResponseEntity.ok(found);
    }

    @PostMapping()
    @ApiOperation(value = "Creates a new director")
    public ResponseEntity<DirectorDTO> createDirector(
            @ApiParam(value = "The director to be created", required = true)
            @RequestBody(required = false) @Valid DirectorDTO director,
            BindingResult bindingResult) {

        requireNonNull(director, "Required fields in the body: (Str) name, (Str) about");
        checkBinding(bindingResult);

        DirectorDTO createdDirector = directorService.addDirector(director);
        URI location = URI.create(String.format("/api/moviedb-app/directors/%s", createdDirector.getId()));
        return ResponseEntity.created(location).body(createdDirector);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a director")
    public ResponseEntity<DirectorDTO> deleteDirectorById(
            @ApiParam(value = "ID of the director to be deleted", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        DirectorDTO deleted = directorService.removeDirector(isNumeric(id));
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an existing director")
    public ResponseEntity<DirectorDTO> updateDirector(
            @ApiParam(value = "ID of the director to be updated", required = true, type = "Long")
            @PathVariable String id,

            @ApiParam(value = "The updated director to be saved", required = true)
            @RequestBody(required = false) @Valid DirectorDTO director,
            BindingResult bindingResult) {

        requireNonNull(id, "Id must not be null.");
        requireNonNull(director, "Required fields in the body: (Str) name, (Str) about");
        checkBinding(bindingResult);

        directorService.modifyDirector(director, isNumeric(id));
        return ResponseEntity.noContent().build();
    }

}
