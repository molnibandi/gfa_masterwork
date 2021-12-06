package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;

import java.util.List;

public interface FilmStudioService {

    FilmStudioDTO findFilmStudioById(Long id);

    List<FilmStudioDTO> listAllFilmStudios();

    FilmStudioDTO addFilmStudio(FilmStudioDTO filmStudioDTO);

    FilmStudioDTO removeFilmStudio(Long id);

    FilmStudioDTO modifyFilmStudio(FilmStudioDTO filmStudioDTO, Long idOfOrigin);

}
