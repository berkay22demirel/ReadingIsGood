package com.berkay22demirel.readingisgood.controller.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {

    @NotNull
    @Email
    @Length(min = 8, max = 50)
    private final String email;

    @NotNull
    @Length(min = 8, max = 64)
    private final String password;
}
