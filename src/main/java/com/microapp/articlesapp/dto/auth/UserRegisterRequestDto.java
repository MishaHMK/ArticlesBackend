package com.microapp.articlesapp.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microapp.articlesapp.annotations.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "confirmPassword",
        message = "Passwords must match")
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class UserRegisterRequestDto {
    @NotBlank(message = "Email is required")
    @Length(min = 8, max = 30,
            message = "Email size must be between "
                    + "8 and 30 symbols inclusively")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Father name is required")
    private String fatherName;
    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 20,
            message = "Password size must be between "
                    + "8 and 20 symbols inclusively")
    private String password;
    @NotBlank(message = "Password confirm is required")
    @Length(min = 8, max = 20,
            message = "Password size must be between "
                    + "8 and 20 symbols inclusively")
    private String confirmPassword;
}
