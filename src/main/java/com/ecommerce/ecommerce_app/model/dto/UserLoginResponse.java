package com.ecommerce.ecommerce_app.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String token;
    private String tokenType = "Bearer";
}
