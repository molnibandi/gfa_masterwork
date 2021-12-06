package com.greenfox.molnibandi_masterwork.repositories;

import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmStudioRepository extends CrudRepository<FilmStudio, Long> {

    @NotNull
    List<FilmStudio> findAll();

    FilmStudio findFilmStudioByName(String name);

    boolean existsFilmStudioByName(String name);

}
