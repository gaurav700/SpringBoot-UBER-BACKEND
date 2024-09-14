package com.UBERAPP.UBER_BACKEND_PROJECT.strategies;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.RideRequest;

public interface RideFareCalculationStrategy {

    static final double RIDE_FARE_MULTIPLIER = 10;
    double calculateFare(RideRequest rideRequest);

}
