package com.ziplink.service;

import com.ziplink.dto.RegisterRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(RegisterRequestDTO request);
}
