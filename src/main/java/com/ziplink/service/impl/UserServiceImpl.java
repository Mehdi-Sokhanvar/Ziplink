package com.ziplink.service.impl;


import com.ziplink.dto.RegisterRequestDTO;
import com.ziplink.exception.custom.UserExistInDatabase;
import com.ziplink.model.Role;
import com.ziplink.model.User;
import com.ziplink.repository.UserRepository;
import com.ziplink.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void register(RegisterRequestDTO request) {
        findByEmail(request.email());
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .credit(0.0)
                .build();

        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    private void findByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserExistInDatabase("User already exists in the database");
        }
    }
}
