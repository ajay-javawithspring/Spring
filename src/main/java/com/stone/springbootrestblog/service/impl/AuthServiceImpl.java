package com.stone.springbootrestblog.service.impl;

import com.stone.springbootrestblog.entity.Role;
import com.stone.springbootrestblog.entity.User;
import com.stone.springbootrestblog.exception.BlogApiException;
import com.stone.springbootrestblog.payload.LoginDto;
import com.stone.springbootrestblog.payload.RegisterDto;
import com.stone.springbootrestblog.repository.RoleRepository;
import com.stone.springbootrestblog.repository.UserRepository;
import com.stone.springbootrestblog.security.JwtTokenProvider;
import com.stone.springbootrestblog.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl() {
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }


    @Override
    public String register(RegisterDto registerDto) {

        if(userRepository.existsByUsername(registerDto.getUsername()))
            throw new BlogApiException(
                    HttpStatus.BAD_REQUEST, String.format("User with Username '%s' already exists",registerDto.getUsername()));
        if(userRepository.existsByEmail(registerDto.getEmail()))
            throw new BlogApiException(
                    HttpStatus.BAD_REQUEST, String.format("User with email '%s' already exists",registerDto.getEmail()));


        User user = new User();

        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        //user.setPassword(registerDto.getPassword());

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        roles.add(roleRepository.findByRole("ROLE_USER").orElseGet(Role::new));
        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully";
    }
}
