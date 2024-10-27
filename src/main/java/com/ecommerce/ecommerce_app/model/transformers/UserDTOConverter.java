package com.ecommerce.ecommerce_app.model.transformers;

import com.ecommerce.ecommerce_app.enums.RoleType;
import com.ecommerce.ecommerce_app.model.dto.UserDTO;
import com.ecommerce.ecommerce_app.model.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTOConverter {

    public static UserDTO convert(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(RoleType.CUSTOMER)
                .build();
    }


    public static List<UserDTO> convert(List<User> users) {
        if (users == null){
            return Collections.emptyList();
        }
        return users
                .stream()
                .map(UserDTOConverter::convert)
                .collect(Collectors.toList());
    }
}

