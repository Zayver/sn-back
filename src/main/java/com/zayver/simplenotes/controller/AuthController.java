package com.zayver.simplenotes.controller;

import com.zayver.simplenotes.dto.request.LoginRequest;
import com.zayver.simplenotes.dto.response.LoginResponse;
import com.zayver.simplenotes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req){
        return authService.login(req);
    }
}
