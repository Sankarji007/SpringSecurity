package com.example.SpringJWT.Service;



import com.example.SpringJWT.Entity.RequestClass;
import com.example.SpringJWT.Entity.Role;
import com.example.SpringJWT.Entity.UserRepository;
import com.example.SpringJWT.Entity.UsersDB;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthendicateService {
    @Autowired
    UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;
    public String register(RequestClass user) {
        UsersDB usersDB=UsersDB.builder()
                .username(user.getUsername())
                        .email(user.getEmail())
                .Password(passwordEncoder.encode(user.getPassword()))
                        .role(user.getRole())
                                .build();

        repository.save(usersDB);
        System.out.println(usersDB);
        String jwt=jwtService.generateToken(usersDB);
        return jwt;
    }

    public String login(UsersDB user) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()

                                )
        );
        UsersDB user1=repository.findByUsername(user.getUsername()).get();
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }
}
