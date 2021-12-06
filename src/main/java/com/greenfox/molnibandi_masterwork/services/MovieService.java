package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.models.dtos.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO findMovieById(Long id);

    List<MovieDTO> listAllMovies();

    MovieDTO addMovie(MovieDTO movieDTO);

    MovieDTO removeMovie(Long id);

    MovieDTO modifyMovie(MovieDTO movieDTO, Long idOfOrigin);

}
