package com.UBERAPP.UBER_BACKEND_PROJECT.services;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User getUserById(Long userId);
}
