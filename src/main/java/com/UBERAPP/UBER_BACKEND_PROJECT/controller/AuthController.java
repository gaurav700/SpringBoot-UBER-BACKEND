package com.UBERAPP.UBER_BACKEND_PROJECT.controller;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.SignUpDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.UserDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.OnBoardDriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO sign){
        return new ResponseEntity<>(authService.signUp(sign), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnBoardDriverDTO onboardDriverDTO ){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId, onboardDriverDTO.getVehicleId()), HttpStatus.CREATED);
    }

}
