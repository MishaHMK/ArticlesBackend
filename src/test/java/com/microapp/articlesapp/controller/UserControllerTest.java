package com.microapp.articlesapp.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.security.jwt.JwtAuthFilter;
import com.microapp.articlesapp.security.jwt.JwtUtil;
import com.microapp.articlesapp.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void receiveCurrentUserInfo_returnsUserDto() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("test@example.com")
                .setFirstName("Bob")
                .setLastName("Test")
                .setRole("AUTHOR");

        Mockito.when(userService.getCurrentUserData()).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));

        Mockito.verify(userService).getCurrentUserData();
    }

    @Test
    void updateCurrentUserInfo_returnsUpdatedUserDto() throws Exception {
        UpdateUserDataDto updateDto = new UpdateUserDataDto()
                .setEmail("test@example.com")
                .setFirstName("Bob")
                .setLastName("Test");

        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("test@example.com")
                .setFirstName("Bob")
                .setLastName("Test")
                .setRole("AUTHOR");

        Mockito.when(userService.updateUser(Mockito.any(UpdateUserDataDto.class))).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));

        Mockito.verify(userService).updateUser(Mockito.any(UpdateUserDataDto.class));
    }

    @Test
    void removeCurrentUser_returnsNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete"))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser();
    }
}
