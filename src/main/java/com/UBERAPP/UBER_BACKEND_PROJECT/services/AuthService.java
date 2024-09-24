package com.UBERAPP.UBER_BACKEND_PROJECT.services;


import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.SignUpDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.UserDTO;

public interface AuthService {
    String[] login(String email, String password);

    UserDTO signUp(SignUpDTO signUpDTO);

    DriverDTO onBoardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
