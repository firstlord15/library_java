package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.user.CreateUserDTO;
import org.ratmir.project.dto.user.UpdateUserDTO;
import org.ratmir.project.dto.user.UserDetailDTO;
import org.ratmir.project.dto.user.UserPublicDTO;
import org.ratmir.project.models.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserPublicDTO toDTO(User user);
    UserDetailDTO toDetailDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    User fromCreatedUser(CreateUserDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    void updateFromDTO(UpdateUserDTO dto, @MappingTarget User user);
}
