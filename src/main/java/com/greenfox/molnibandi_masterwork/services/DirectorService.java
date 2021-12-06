package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.models.dtos.DirectorDTO;

import java.util.List;

public interface DirectorService {

    DirectorDTO findDirectorById(Long id);

    List<DirectorDTO> listAllDirectors();

    DirectorDTO addDirector(DirectorDTO directorDTO);

    DirectorDTO removeDirector(Long id);

    DirectorDTO modifyDirector(DirectorDTO directorDTO, Long idOfOrigin);

}
