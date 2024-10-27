package com.ecommerce.ecommerce_app.service.impl;

import com.ecommerce.ecommerce_app.config.jwt.JwtUtils;
import com.ecommerce.ecommerce_app.enums.RoleType;
import com.ecommerce.ecommerce_app.model.dto.UserLoginDTO;
import com.ecommerce.ecommerce_app.model.dto.UserLoginResponse;
import com.ecommerce.ecommerce_app.model.dto.UserRegistrationDTO;
import com.ecommerce.ecommerce_app.model.entity.Role;
import com.ecommerce.ecommerce_app.model.entity.User;
import com.ecommerce.ecommerce_app.repository.RoleRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public static final String TOKEN_PREFIX = "Bearer ";



    @Override
    public String registerUser(UserRegistrationDTO userRegistrationDTO) {

        if (userRepository.existsByUsername(userRegistrationDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        Role defaultRole = roleRepository.findByName(RoleType.CUSTOMER);

        User user = User.builder()
                .firstName(userRegistrationDTO.getFirstName())
                .lastName(userRegistrationDTO.getLastName())
                .username(userRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                .email(userRegistrationDTO.getEmail())
                .phoneNumber(userRegistrationDTO.getPhoneNumber())
                .roles(Set.of(defaultRole))
                .build();

        userRepository.save(user);
        return "User with username: " + user.getUsername() + " is registered successfully !";

    }

    @Override
    public UserLoginResponse login(UserLoginDTO userLoginDTO, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginDTO.getUsernameOrEmail(),
                    userLoginDTO.getPassword()
            ));

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userLoginDTO.getUsernameOrEmail(), userLoginDTO.getUsernameOrEmail());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid username/email or password.");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username/email or password.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);

        return new UserLoginResponse(token,TOKEN_PREFIX);
    }
}
