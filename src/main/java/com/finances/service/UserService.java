package com.finances.service;

import com.finances.dto.request.LoginRequest;
import com.finances.dto.request.RegisterRequest;
import com.finances.exception.AttributeAlreadyExistsException;
import com.finances.exception.ShortAttributeException;
import com.finances.model.User;
import com.finances.repository.UserRepo;
import com.finances.exception.NonExistentEntityException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;

    public UserService(UserRepo userRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest loginRequest) throws NonExistentEntityException {
        User currentUser = userRepo.findByUsername(loginRequest.username())
                .orElseThrow(() -> new NonExistentEntityException("This user does not exists"));

        return jwtService
                .generateToken(currentUser.getUsername(), currentUser.getRole().toString(), currentUser.getEmail());
    }

    public String register(RegisterRequest registerRequest) {
        if (userRepo.findByUsername(registerRequest.username()).isPresent())
            throw new AttributeAlreadyExistsException("This username is taken");

        if (userRepo.findByEmail(registerRequest.email()).isPresent())
            throw new AttributeAlreadyExistsException("This email is taken");

        if (registerRequest.password().length() < 5)
            throw new ShortAttributeException("The password must be more than 5 characters");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User newUser = new User();
        newUser.setEmail(registerRequest.email());
        newUser.setRole(User.Role.USER);
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        userRepo.save(newUser);

        return "User registered successfully";
    }
}
