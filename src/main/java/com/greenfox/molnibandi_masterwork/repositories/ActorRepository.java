package com.greenfox.molnibandi_masterwork.repositories;

import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<Actor, Long> {

    @NotNull
    List<Actor> findAll();

    boolean existsActorByName(String name);

}
