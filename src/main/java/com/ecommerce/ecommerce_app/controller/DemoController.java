package com.ecommerce.ecommerce_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This endpoint is accessible by all users, regardless of roles!";
    }

    @GetMapping("/admin/j")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "This endpoint is only accessible by ADMIN!";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerEndpoint() {
        return "This endpoint is only accessible by CUSTOMER!";
    }

    @PostMapping("/admin/action")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPostAction() {
        return "This POST action is only allowed for ADMIN!";
    }

    @PostMapping("/customer/action")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customerPostAction() {
        return "This POST action is only allowed for CUSTOMER!";
    }

}
