package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;


import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.SignUpDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.UserDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.Role;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.RuntimeConflictException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.UserRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.security.JWTService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.AuthService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.DriverService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RiderService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new String[]{accessToken, refreshToken};
    }

    @Override
    @Transactional
    public UserDTO signUp(SignUpDTO signUpDTO) {

        User u = userRepository.findByEmail(signUpDTO.getEmail()).orElse(null);
        if(u!=null){
            throw new RuntimeConflictException("Cannot signUp with this email, mail is already exists "+ signUpDTO.getEmail());
        }

        User user = modelMapper.map(signUpDTO, User.class);
        user.setRoles(Set.of(Role.RIDER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);

        // create user related entities
        riderService.createNewRider(save);

        // add wallet related service here
        walletService.createNewWallet(save);

        return modelMapper.map(save, UserDTO.class);
    }

    @Override
    public DriverDTO onBoardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id :"+userId));

        if(user.getRoles().contains(Role.DRIVER)){
            throw new RuntimeException("User is already a Driver");
        }

        Driver driver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(driver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with this id :"+userId));
        return jwtService.generateAccessToken(user);
    }
}
