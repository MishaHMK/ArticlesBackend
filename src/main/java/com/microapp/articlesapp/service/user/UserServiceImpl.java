package com.microapp.articlesapp.service.user;

import com.microapp.articlesapp.dto.auth.UserRegisterRequestDto;
import com.microapp.articlesapp.dto.auth.UserRegisterResponseDto;
import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.exception.RegisterException;
import com.microapp.articlesapp.mapper.UserMapper;
import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.user.UserRepository;
import com.microapp.articlesapp.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegisterResponseDto save(UserRegisterRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegisterException("User with email "
                    + requestDto.getEmail() + " already exists");
        }
        User user = userMapper.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public UserDto getCurrentUserData() {
        return userMapper.toUserDto(SecurityUtil.getLoggedInUser());
    }

    @Override
    public void deleteUser() {
        User user = SecurityUtil.getLoggedInUser();
        userRepository.deleteById(user.getId());
    }

    @Override
    public UserDto updateUser(UpdateUserDataDto updateDto) {
        User currentUser = SecurityUtil.getLoggedInUser();
        userMapper.updateFromDto(updateDto, currentUser);
        return userMapper.toUserDto(userRepository.save(currentUser));
    }
}
