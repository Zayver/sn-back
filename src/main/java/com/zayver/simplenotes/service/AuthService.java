package com.zayver.simplenotes.service;

import com.zayver.simplenotes.dto.request.LoginRequest;
import com.zayver.simplenotes.dto.response.LoginResponse;
import com.zayver.simplenotes.model.User;
import com.zayver.simplenotes.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Optional<User> getUserByUsername(@NonNull String username) {
        return this.userRepository.findByUsername(username);
    }
    protected User getUserByUuid(@NonNull UUID uuid){
        return this.userRepository.findById(uuid).orElseThrow();
    }

    public LoginResponse login(LoginRequest req) {
        val user = this.getUserByUsername(req.getUsername()).orElseThrow( ()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return LoginResponse.builder().token(jwtService.generateToken(user)).build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
