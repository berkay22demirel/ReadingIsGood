package com.berkay22demirel.readingisgood.security;

import com.berkay22demirel.readingisgood.entity.User;
import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtManager {

    public User validateTokenByUserId(HttpServletRequest httpServletRequest, Long userId) {
        User user = (User) ((UsernamePasswordAuthenticationToken) httpServletRequest.getUserPrincipal()).getPrincipal();
        if (user == null || !user.getId().equals(userId)) {
            throw new AuthorizationException("The user does not have access authorization!");
        }
        return user;
    }
}
