package com.UBERAPP.UBER_BACKEND_PROJECT.strategies;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;
    double calculateFare(RideRequest rideRequest);

}
