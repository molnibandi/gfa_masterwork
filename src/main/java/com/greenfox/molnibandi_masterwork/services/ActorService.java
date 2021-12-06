package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;

import java.util.List;

public interface ActorService {

    ActorDTO findActorById(Long id);

    List<ActorDTO> listAllActors();

    ActorDTO addActor(ActorDTO actorDTO);

    ActorDTO removeActor(Long id);

    ActorDTO modifyActor(ActorDTO actorDTO, Long idOfOrigin);

}
