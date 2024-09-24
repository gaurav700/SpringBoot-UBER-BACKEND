package com.UBERAPP.UBER_BACKEND_PROJECT.repositories;


import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rider;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
