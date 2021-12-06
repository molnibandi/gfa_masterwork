package com.greenfox.molnibandi_masterwork.services;

import com.greenfox.molnibandi_masterwork.exceptions.types.EntityNotFoundException;
import com.greenfox.molnibandi_masterwork.models.dtos.DirectorDTO;
import com.greenfox.molnibandi_masterwork.models.entities.Director;
import com.greenfox.molnibandi_masterwork.repositories.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNonNull;
import static com.greenfox.molnibandi_masterwork.services.ValidationService.requireNull;

@Service
@Transactional
public class DefaultDirectorService implements DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DefaultDirectorService(
            DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public DirectorDTO findDirectorById(Long id) {
        requireNonNull(id, "Id must not be null!");

        Director director = directorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Director with ID: " + id + " cannot be found."));

        return DirectorDTO.convertToDTOForListing(director);
    }

    @Override
    public List<DirectorDTO> listAllDirectors() {
        return directorRepository.findAll().stream()
                .map(DirectorDTO::convertToDTOForListing)
                .collect(Collectors.toList());
    }

    @Override
    public DirectorDTO addDirector(DirectorDTO director) {
        requireNonNull(director, "Director must not be null!");
        requireNull(director.getId(), "Director ID must be null!");

        if (directorRepository.existsDirectorByName(director.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        Director saved = directorRepository.save(director.convertToDirector());

        return DirectorDTO.convertToDTOForSaving(saved);
    }

    @Override
    public DirectorDTO removeDirector(Long id) {
        requireNonNull(id, "Id must not be null!");

        Director director = directorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Director with ID: " + id + " cannot be found."));

        directorRepository.delete(director);

        return DirectorDTO.convertToDTOForSaving(director);
    }

    @Override
    public DirectorDTO modifyDirector(DirectorDTO director, Long idOfOrigin) {
        requireNonNull(director, "Director must not be null!");
        requireNonNull(idOfOrigin, "ID of original director must not be null!");

        Director originalDirector = directorRepository.findById(idOfOrigin).orElseThrow(
                () -> new EntityNotFoundException(
                        "Director with ID: " + idOfOrigin + " cannot be found."));

        if (!originalDirector.getId().equals(director.getId())) {
            if (directorRepository.existsById(director.getId())) {
                throw new IllegalArgumentException("ID must be unique.");
            }
        }

        if (directorRepository.existsDirectorByName(director.getName()) &&
                !director.getName().equals(originalDirector.getName())) {
            throw new IllegalArgumentException("Name must be unique.");
        }

        director.setId(idOfOrigin);
        directorRepository.save(director.convertToDirector());

        return director;
    }

}
