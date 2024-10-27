package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.model.dto.UserDTO;
import com.ecommerce.ecommerce_app.model.dto.UserLoginDTO;
import com.ecommerce.ecommerce_app.model.dto.UserLoginResponse;
import com.ecommerce.ecommerce_app.model.dto.UserRegistrationDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String registerUser(UserRegistrationDTO userRegistrationDTO);

    UserLoginResponse login(UserLoginDTO userLoginDTO, HttpServletResponse response);

}
