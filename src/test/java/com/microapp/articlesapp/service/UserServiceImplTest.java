package com.microapp.articlesapp.service;

import com.microapp.articlesapp.dto.auth.UserRegisterRequestDto;
import com.microapp.articlesapp.dto.auth.UserRegisterResponseDto;
import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.exception.RegisterException;
import com.microapp.articlesapp.mapper.UserMapper;
import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.user.UserRepository;
import com.microapp.articlesapp.security.SecurityUtil;
import com.microapp.articlesapp.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_userAlreadyExists_throwsException() {
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setEmail("test@example.com");

        Mockito.when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        Assertions.assertThrows(RegisterException.class,
                () -> userService.save(requestDto));
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void save_newUser_success() {
        UserRegisterRequestDto requestDto = new UserRegisterRequestDto();
        requestDto.setEmail("new@example.com");
        requestDto.setPassword("test_password");

        User user = new User();
        user.setPassword("test_password");

        UserRegisterResponseDto responseDto = new UserRegisterResponseDto();

        Mockito.when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        Mockito.when(userMapper.toUser(requestDto)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded");
        Mockito.when(userMapper.toResponse(user)).thenReturn(responseDto);

        UserRegisterResponseDto result = userService.save(requestDto);

        Assertions.assertEquals(responseDto, result);
        Assertions.assertEquals("encoded", user.getPassword());
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void getCurrentUserData_success() {
        User user = new User();
        UserDto userDto = new UserDto();

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(user);
            Mockito.when(userMapper.toUserDto(user)).thenReturn(userDto);

            UserDto result = userService.getCurrentUserData();

            Assertions.assertEquals(userDto, result);
        }
    }

    @Test
    void deleteUser_callsRepositoryDelete() {
        User user = new User();
        user.setId(1L);

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(user);

            userService.deleteUser();

            Mockito.verify(userRepository).deleteById(1L);
        }
    }

    @Test
    void updateUser_success() {
        User user = new User();
        UserDto userDto = new UserDto();
        UpdateUserDataDto updateDto = new UpdateUserDataDto();

        try (MockedStatic<SecurityUtil> securityUtilMock =
                     Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMock.when(SecurityUtil::getLoggedInUser).thenReturn(user);
            Mockito.doNothing().when(userMapper).updateFromDto(updateDto, user);
            Mockito.when(userRepository.save(user)).thenReturn(user);
            Mockito.when(userMapper.toUserDto(user)).thenReturn(userDto);

            UserDto result = userService.updateUser(updateDto);

            Assertions.assertEquals(userDto, result);
            Mockito.verify(userMapper).updateFromDto(updateDto, user);
            Mockito.verify(userRepository).save(user);
        }
    }
}
