package com.stone.springbootrestblog.service;

import com.stone.springbootrestblog.payload.LoginDto;
import com.stone.springbootrestblog.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
