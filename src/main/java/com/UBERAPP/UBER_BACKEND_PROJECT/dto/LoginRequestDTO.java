package com.UBERAPP.UBER_BACKEND_PROJECT.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
