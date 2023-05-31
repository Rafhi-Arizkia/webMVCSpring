package com.example.webmvcspring.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class RegisterDto {
    @NotEmpty(message = "Username not null")
    private String username;
    @NotEmpty(message = "Email not null")
    @Email(message = "Required email format @")
    private String email;
    @NotEmpty(message = "Password not null")
    private String password;

}
