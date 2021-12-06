package com.greenfox.molnibandi_masterwork.controllers;

import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;
import com.greenfox.molnibandi_masterwork.services.ActorService;
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
@RequestMapping("/api/moviedb-app/actors")
public class ActorRestController {

    private final ActorService actorService;

    @Autowired
    public ActorRestController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping()
    @ApiOperation(value = "Lists all actors")
    public ResponseEntity<List<ActorDTO>> listActors() {
        return ResponseEntity.ok(actorService.listAllActors());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns an actor with the given ID")
    public ResponseEntity<ActorDTO> getActorById(
            @ApiParam(value = "ID of the queried actor", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        ActorDTO found = actorService.findActorById(isNumeric(id));
        return ResponseEntity.ok(found);
    }

    @PostMapping()
    @ApiOperation(value = "Creates a new actor")
    public ResponseEntity<ActorDTO> createActor(
            @ApiParam(value = "The actor to be created", required = true)
            @RequestBody(required = false) @Valid ActorDTO actor,
            BindingResult bindingResult) {

        requireNonNull(actor, "Required fields in the body: (Str) name, (Str) about");
        checkBinding(bindingResult);

        ActorDTO createdActor = actorService.addActor(actor);
        URI location = URI.create(String.format("/api/moviedb-app/actors/%s", createdActor.getId()));
        return ResponseEntity.created(location).body(createdActor);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an actor")
    public ResponseEntity<ActorDTO> deleteActorById(
            @ApiParam(value = "ID of the actor to be deleted", required = true, type = "Long")
            @PathVariable String id) {

        requireNonNull(id, "Id must not be null.");

        ActorDTO deleted = actorService.removeActor(isNumeric(id));
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an existing actor")
    public ResponseEntity<ActorDTO> updateActor(
            @ApiParam(value = "ID of the actor to be updated", required = true, type = "Long")
            @PathVariable String id,

            @ApiParam(value = "The updated actor to be saved", required = true)
            @RequestBody(required = false) ActorDTO actor,
            BindingResult bindingResult) {

        requireNonNull(id, "Id must not be null.");
        requireNonNull(actor, "Required fields in the body: (Str) name, (Str) about");
        checkBinding(bindingResult);

        actorService.modifyActor(actor, isNumeric(id));
        return ResponseEntity.noContent().build();
    }

}
