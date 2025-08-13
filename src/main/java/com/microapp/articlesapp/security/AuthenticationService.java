package com.microapp.articlesapp.security;

import com.microapp.articlesapp.dto.auth.UserLoginRequestDto;
import com.microapp.articlesapp.dto.auth.UserLoginResponseDto;
import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.user.UserRepository;
import com.microapp.articlesapp.security.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User byEmail = userRepository.findByEmail(requestDto.email())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with this email address does not exist")
                );

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        String token = jwtUtil.generateToken(authentication.getName(), byEmail.getId());
        return new UserLoginResponseDto(token);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}
