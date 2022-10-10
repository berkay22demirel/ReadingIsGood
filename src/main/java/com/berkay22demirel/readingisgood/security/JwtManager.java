package com.berkay22demirel.readingisgood.security;

import com.berkay22demirel.readingisgood.entity.Customer;
import com.berkay22demirel.readingisgood.exception.AuthorizationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtManager {

    public Customer validateTokenByCustomerId(HttpServletRequest httpServletRequest, Long customerId) {
        Customer customer = (Customer) ((UsernamePasswordAuthenticationToken) httpServletRequest.getUserPrincipal()).getPrincipal();
        if (customer == null || !customer.getId().equals(customerId)) {
            throw new AuthorizationException("The user does not have access authorization!");
        }
        return customer;
    }

    public Customer getCustomer(HttpServletRequest httpServletRequest) {
        return (Customer) ((UsernamePasswordAuthenticationToken) httpServletRequest.getUserPrincipal()).getPrincipal();
    }
}
