package com.company.company_portal.service;

import com.company.company_portal.dto.LoginRequest;
import com.company.company_portal.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest req);
    String login(LoginRequest req);
}
