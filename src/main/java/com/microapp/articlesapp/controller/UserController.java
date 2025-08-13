package com.microapp.articlesapp.controller;

import com.microapp.articlesapp.dto.userdata.UpdateUserDataDto;
import com.microapp.articlesapp.dto.userdata.UserDto;
import com.microapp.articlesapp.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User controller", description = "User management endpoint")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("me")
    @Operation(summary = "Get user info",
            description = "Receive currently logged in user info")
    public UserDto receiveCurrentUserInfo() {
        return userService.getCurrentUserData();
    }

    @PutMapping("update")
    @Operation(summary = "Update user info",
            description = "Update currently logged in user profile info")
    public UserDto updateCurrentUserInfo(@RequestBody UpdateUserDataDto userDto) {
        return userService.updateUser(userDto);
    }

    @PutMapping("delete")
    @Operation(summary = "Remove current user account",
            description = "Remove currently logged in user profile")
    public void removeCurrentUser() {
        userService.deleteUser();
    }
}
