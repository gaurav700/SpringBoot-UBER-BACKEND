package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.UserRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    @Override
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with this id not found"));
    }


}
