package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.model.dto.UserLoginDTO;
import com.ecommerce.ecommerce_app.model.dto.UserLoginResponse;
import com.ecommerce.ecommerce_app.model.dto.UserRegistrationDTO;
import com.ecommerce.ecommerce_app.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return ResponseEntity.ok(authService.registerUser(userRegistrationDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(userLoginDTO, response));
    }


}
