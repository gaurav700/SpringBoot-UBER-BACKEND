package com.UBERAPP.UBER_BACKEND_PROJECT.repositories;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rating;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);
    Optional<Rating> findByRide(Ride ride);
}
