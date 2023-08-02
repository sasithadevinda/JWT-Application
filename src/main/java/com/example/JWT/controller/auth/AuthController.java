package com.example.JWT.controller.auth;

import com.example.JWT.auth.AuthRequest;
import com.example.JWT.auth.AuthResponse;
import com.example.JWT.entity.User;
import com.example.JWT.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
         User user=   (User) authenticate.getPrincipal();
         String accessToken = jwtTokenUtil.generateAccessToken(user);
         AuthResponse authResponse= new AuthResponse(user.getEmail(), accessToken);
         return ResponseEntity.ok(authResponse);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

}
