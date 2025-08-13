package com.microapp.articlesapp.mapper;

import com.microapp.articlesapp.config.MapperConfig;
import com.microapp.articlesapp.dto.auth.UserRegisterRequestDto;
import com.microapp.articlesapp.dto.auth.UserRegisterResponseDto;
import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toUser(UserRegisterRequestDto dto);

    UserDto toUserDto(User user);

    UserRegisterResponseDto toResponse(User user);

    void updateFromDto(UpdateUserDataDto dto, @MappingTarget User user);
}
