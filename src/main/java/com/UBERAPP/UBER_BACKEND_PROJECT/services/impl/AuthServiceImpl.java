package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;


import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.SignUpDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.UserDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.Role;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.RuntimeConflictException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.UserRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.AuthService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDTO signUp(SignUpDTO signUpDTO) {

        User u = userRepository.findByEmail(signUpDTO.getEmail()).orElse(null);
        if(u!=null){
            throw new RuntimeConflictException("Cannot signUp with this email, mail is already exists "+ signUpDTO.getEmail());
        }

        User user = modelMapper.map(signUpDTO, User.class);
        user.setRoles(Set.of(Role.RIDER));
        User save = userRepository.save(user);

        // create user related entities
        riderService.createNewRider(user);

        // add wallet related service here

        return modelMapper.map(save, UserDTO.class);
    }

    @Override
    public DriverDTO onBoardNewDriver(Long userId) {
        return null;
    }
}
