package com.finances.service;

import com.finances.dto.request.LoginRequest;
import com.finances.dto.request.RegisterRequest;
import com.finances.exception.AttributeAlreadyExistsException;
import com.finances.exception.IncorrectAttributeException;
import com.finances.exception.ShortAttributeException;
import com.finances.model.User;
import com.finances.repository.UserRepo;
import com.finances.exception.NonExistentEntityException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest loginRequest) throws NonExistentEntityException {
        User currentUser = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IncorrectAttributeException("Incorrect credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), currentUser.getPassword())) {
            throw new IncorrectAttributeException("Incorrect credentials");
        }

        return jwtService
                .generateToken(currentUser.getUsername(), currentUser.getRole().toString(), currentUser.getEmail());
    }

    public String register(RegisterRequest registerRequest) {
        if (userRepo.findByUsername(registerRequest.getUsername()).isPresent())
            throw new AttributeAlreadyExistsException("This username is taken");

        if (userRepo.findByEmail(registerRequest.getEmail()).isPresent())
            throw new AttributeAlreadyExistsException("This email is taken");

        if (registerRequest.getPassword().length() < 5)
            throw new ShortAttributeException("The password must be more than 5 characters");

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setRole(User.Role.USER);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepo.save(newUser);

        return "User registered successfully";
    }
}
