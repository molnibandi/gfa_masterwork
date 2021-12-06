package com.greenfox.molnibandi_masterwork.repositories;

import com.greenfox.molnibandi_masterwork.models.entities.Director;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectorRepository extends CrudRepository<Director, Long> {

    @NotNull
    List<Director> findAll();

    Director findDirectorByName(String name);

    boolean existsDirectorByName(String name);

}
