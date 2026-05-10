package com.food.login.service;

import com.food.login.client.RegisterServiceClient;
import com.food.login.dto.LoginRequest;
import com.food.login.dto.LoginResponse;
import com.food.login.dto.UserLookupResponse;
import com.food.login.model.LoginAudit;
import com.food.login.repository.LoginAuditRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final RegisterServiceClient registerServiceClient;
    private final LoginAuditRepository loginAuditRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(RegisterServiceClient registerServiceClient,
                       LoginAuditRepository loginAuditRepository,
                       JwtService jwtService) {
        this.registerServiceClient = registerServiceClient;
        this.loginAuditRepository = loginAuditRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponse login(LoginRequest request) {
        UserLookupResponse user;

        try {
            user = registerServiceClient.getUserByUsername(request.getUsername());
        } catch (FeignException.NotFound ex) {
            loginAuditRepository.save(new LoginAudit(request.getUsername(), false, LocalDateTime.now()));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        boolean validPassword = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!validPassword) {
            loginAuditRepository.save(new LoginAudit(request.getUsername(), false, LocalDateTime.now()));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        loginAuditRepository.save(new LoginAudit(request.getUsername(), true, LocalDateTime.now()));
        String token = jwtService.generateToken(user.getUsername());
        return new LoginResponse(token, "Bearer");
    }
}
