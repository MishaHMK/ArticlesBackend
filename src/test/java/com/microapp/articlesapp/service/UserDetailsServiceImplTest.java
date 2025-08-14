package com.microapp.articlesapp.service;

import com.microapp.articlesapp.model.User;
import com.microapp.articlesapp.repository.user.UserRepository;
import com.microapp.articlesapp.security.UserDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_userExists_success() {
        String email = "test@example.com";
        User user = new User().setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(email);

        Assertions.assertEquals(user, result);
        Mockito.verify(userRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_userNotFound_throwsException() {
        String email = "missing@example.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email));

        Mockito.verify(userRepository).findByEmail(email);
    }
}
