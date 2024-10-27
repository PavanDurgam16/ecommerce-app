package com.ecommerce.ecommerce_app.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
public class UserLoginDTO {
    @NotBlank(message = "Username or Email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
