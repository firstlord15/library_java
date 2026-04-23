package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.author.AuthorDetailDTO;
import org.ratmir.project.dto.author.CreateAuthorDTO;
import org.ratmir.project.dto.author.UpdateAuthorDTO;
import org.ratmir.project.models.Author;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {
    AuthorDetailDTO toDTO(Author author);

    @Mapping(target = "id", ignore = true)
    Author fromCreatedDTO(CreateAuthorDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(UpdateAuthorDTO dto, @MappingTarget Author author);
}
