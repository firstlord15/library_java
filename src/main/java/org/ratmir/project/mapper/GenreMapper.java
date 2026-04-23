package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.genre.CreateGenreDTO;
import org.ratmir.project.dto.genre.GenreDTO;
import org.ratmir.project.models.Genre;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GenreMapper {
    GenreDTO toDTO(Genre genre);

    @Mapping(target = "id", ignore = true)
    Genre fromCreatedUser(CreateGenreDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(CreateGenreDTO dto, @MappingTarget Genre genre);
}
