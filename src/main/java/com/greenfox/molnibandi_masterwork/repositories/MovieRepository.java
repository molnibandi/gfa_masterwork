package com.greenfox.molnibandi_masterwork.repositories;

import com.greenfox.molnibandi_masterwork.models.entities.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @NotNull
    List<Movie> findAll();

    boolean existsMovieByTitle(String title);

}
