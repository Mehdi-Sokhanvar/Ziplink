package com.ziplink.controller;

import com.ziplink.dto.RegisterRequestDTO;
import com.ziplink.dto.UserResponseDTO;
import com.ziplink.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request){
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponseDTO("User register successfully",request.email()));
    }


}
