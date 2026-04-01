package com.company.company_portal.service.impl;

import com.company.company_portal.config.JwtUtil;
import com.company.company_portal.dto.LoginRequest;
import com.company.company_portal.dto.RegisterRequest;
import com.company.company_portal.entity.Employee;
import com.company.company_portal.entity.Role;
import com.company.company_portal.entity.User;
import com.company.company_portal.exception.BadRequestException;
import com.company.company_portal.exception.ResourceAlreadyExistsException;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.UserRepository;
import com.company.company_portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest req) {
        if(userRepository.existsByUsername(req.username())) {
            throw new ResourceAlreadyExistsException("Username already exists!!");
        }

        User user = User.builder()
                .username(req.username())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(req.role())
                .build();

        User savedUser = userRepository.save(user);
    }

    public String login(LoginRequest req) {
        User user = userRepository.findByUsername(req.username())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid credentials!!"));

        if(!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials!!");
        }

        return jwtUtil.generateToken(user);
    }
}
