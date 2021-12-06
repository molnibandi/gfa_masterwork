package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;
import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import com.greenfox.molnibandi_masterwork.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNonNull;
import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNull;

@Service
@Transactional
public class DefaultActorService implements ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public DefaultActorService(
            ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public ActorDTO findActorById(Long id) {
        requireNonNull(id, "Id must not be null!");

        Actor actor = actorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Actor with ID: " + id + " cannot be found."));

        return ActorDTO.convertToDTOForListing(actor);
    }

    @Override
    public List<ActorDTO> listAllActors() {
        return actorRepository.findAll().stream()
                .map(ActorDTO::convertToDTOForListing)
                .collect(Collectors.toList());
    }

    @Override
    public ActorDTO addActor(ActorDTO actor) {
        requireNonNull(actor, "Actor must not be null!");
        requireNull(actor.getId(), "Actor ID must be null!");

        if (actorRepository.existsActorByName(actor.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        Actor saved = actorRepository.save(actor.convertToActor());

        return ActorDTO.convertToDTOForSaving(saved);
    }

    @Override
    public ActorDTO removeActor(Long id) {
        requireNonNull(id, "Id must not be null!");

        Actor actor = actorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Actor with ID: " + id + " cannot be found."));

        actorRepository.delete(actor);

        return ActorDTO.convertToDTOForSaving(actor);
    }

    @Override
    public ActorDTO modifyActor(ActorDTO actor, Long idOfOrigin) {
        requireNonNull(actor, "Actor must not be null!");
        requireNonNull(idOfOrigin, "ID of original actor must not be null!");

        Actor originalActor = actorRepository.findById(idOfOrigin).orElseThrow(
                () -> new EntityNotFoundException(
                        "Actor with ID: " + idOfOrigin + " cannot be found."));

        if (!originalActor.getId().equals(actor.getId())) {
            if (actorRepository.existsById(actor.getId())) {
                throw new IllegalArgumentException("ID must be unique.");
            }
        }

        if (actorRepository.existsActorByName(actor.getName()) &&
        !actor.getName().equals(originalActor.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        actor.setId(idOfOrigin);
        actorRepository.save(actor.convertToActor());

        return actor;
    }

}
