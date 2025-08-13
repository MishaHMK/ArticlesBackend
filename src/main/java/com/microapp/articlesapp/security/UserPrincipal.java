package com.microapp.articlesapp.security;

import lombok.Getter;

@Getter
public class UserPrincipal {
    private final Long userId;
    private final String username;

    public UserPrincipal(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
