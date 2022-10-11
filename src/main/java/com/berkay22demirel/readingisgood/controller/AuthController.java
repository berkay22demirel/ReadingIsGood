package com.berkay22demirel.readingisgood.controller;

import com.berkay22demirel.readingisgood.controller.request.AuthRequest;
import com.berkay22demirel.readingisgood.controller.response.Response;
import com.berkay22demirel.readingisgood.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping
    public ResponseEntity<Response> authenticateUser(@RequestBody @Valid AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new Response("Token created successfully.", token));
    }
}
