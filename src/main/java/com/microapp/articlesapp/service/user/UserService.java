package com.microapp.articlesapp.service.user;

import com.microapp.articlesapp.dto.auth.UserRegisterRequestDto;
import com.microapp.articlesapp.dto.auth.UserRegisterResponseDto;
import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.exception.RegisterException;

public interface UserService {
    UserRegisterResponseDto save(UserRegisterRequestDto userRegisterRequestDto)
            throws RegisterException;

    UserDto getCurrentUserData();

    UserDto updateUser(UpdateUserDataDto updateDto);

    void deleteUser();
}
