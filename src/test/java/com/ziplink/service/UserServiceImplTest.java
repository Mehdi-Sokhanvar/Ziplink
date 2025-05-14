package com.ziplink.service;

import com.ziplink.dto.RegisterRequestDTO;
import com.ziplink.exception.custom.UserExistInDatabase;
import com.ziplink.model.Role;
import com.ziplink.model.User;
import com.ziplink.repository.UserRepository;
import com.ziplink.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testRegister_SuccessRegistration() {

        RegisterRequestDTO request = new RegisterRequestDTO("sample@gmail.com", "123456789");

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        userService.register(request);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("sample@gmail.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(Role.USER, savedUser.getRole());
        assertEquals(0.0, savedUser.getCredit());
    }

    @Test
    void testRegister_EmailAlreadyExists_ThrowsException() {

        RegisterRequestDTO request = new RegisterRequestDTO("duplicate@example.com", "pass1");
        userService.register(request);


        RegisterRequestDTO duplicateRequest = new RegisterRequestDTO("duplicate@example.com", "pass2");
        assertThrows(UserExistInDatabase.class, () -> userService.register(duplicateRequest));

    }
}