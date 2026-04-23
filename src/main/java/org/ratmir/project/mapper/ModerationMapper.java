package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.moderation.ModerationRecordDTO;
import org.ratmir.project.models.ModerationRecord;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModerationMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "moderatorId", source = "moderator.id")
    @Mapping(target = "moderatorUsername", source = "moderator.username")
    ModerationRecordDTO toDTO(ModerationRecord dto);
}
