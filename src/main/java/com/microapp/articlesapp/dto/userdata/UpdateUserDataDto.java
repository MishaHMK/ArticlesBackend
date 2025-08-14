package com.microapp.articlesapp.dto.userdata;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateUserDataDto {
    private String email;
    private String firstName;
    private String lastName;
}
