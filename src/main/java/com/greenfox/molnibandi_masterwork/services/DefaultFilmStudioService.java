package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.FilmStudioDTO;
import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import com.greenfox.molnibandi_masterwork.repositories.FilmStudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNonNull;
import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNull;

@Service
@Transactional
public class DefaultFilmStudioService implements FilmStudioService {

    private final FilmStudioRepository filmStudioRepository;

    @Autowired
    public DefaultFilmStudioService(
            FilmStudioRepository filmStudioRepository) {
        this.filmStudioRepository = filmStudioRepository;
    }

    @Override
    public FilmStudioDTO findFilmStudioById(Long id) {
        requireNonNull(id, "Id must not be null!");

        FilmStudio filmStudio = filmStudioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Film studio with ID: " + id + " cannot be found."));

        return FilmStudioDTO.convertToDTOForListing(filmStudio);
    }

    @Override
    public List<FilmStudioDTO> listAllFilmStudios() {
        return filmStudioRepository.findAll().stream()
                .map(FilmStudioDTO::convertToDTOForListing)
                .collect(Collectors.toList());
    }

    @Override
    public FilmStudioDTO addFilmStudio(FilmStudioDTO filmStudio) {
        requireNonNull(filmStudio, "Film studio must not be null!");
        requireNull(filmStudio.getId(), "Film studio ID must be null!");

        if (filmStudioRepository.existsFilmStudioByName(filmStudio.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        FilmStudio saved = filmStudioRepository.save(filmStudio.convertToFilmStudio());

        return FilmStudioDTO.convertToDTOForSaving(saved);
    }

    @Override
    public FilmStudioDTO removeFilmStudio(Long id) {
        requireNonNull(id, "Id must not be null!");

        FilmStudio filmStudio = filmStudioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Film studio with ID: " + id + " cannot be found."));

        filmStudioRepository.delete(filmStudio);

        return FilmStudioDTO.convertToDTOForSaving(filmStudio);
    }

    @Override
    public FilmStudioDTO modifyFilmStudio(FilmStudioDTO filmStudio, Long idOfOrigin) {
        requireNonNull(filmStudio, "Film studio must not be null!");
        requireNonNull(idOfOrigin, "ID of original film studio must not be null!");

        FilmStudio originalFilmStudio = filmStudioRepository.findById(idOfOrigin).orElseThrow(
                () -> new EntityNotFoundException(
                        "Film studio with ID: " + idOfOrigin + " cannot be found."));

        if (!originalFilmStudio.getId().equals(filmStudio.getId())) {
            if (filmStudioRepository.existsById(filmStudio.getId())) {
                throw new IllegalArgumentException("ID must be unique.");
            }
        }

        if (filmStudioRepository.existsFilmStudioByName(filmStudio.getName()) &&
                !filmStudio.getName().equals(originalFilmStudio.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        filmStudio.setId(idOfOrigin);
        filmStudioRepository.save(filmStudio.convertToFilmStudio());

        return filmStudio;
    }

}
