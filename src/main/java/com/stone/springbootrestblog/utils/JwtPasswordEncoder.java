package com.stone.springbootrestblog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtPasswordEncoder {

    public static void main(String[] args) {

        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(" encoded  " +bCryptPasswordEncoder.encode("admin"));
        System.out.println(" encoded  " +bCryptPasswordEncoder.encode("ramesh"));

    }
}

