package com.UBERAPP.UBER_BACKEND_PROJECT.services;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RiderDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rider;

public interface RatingService {
    DriverDTO rateDriver(Ride ride, Integer rating);
    RiderDTO rateRider(Ride ride, Integer rating);
    void createNewRating(Ride ride);
}
