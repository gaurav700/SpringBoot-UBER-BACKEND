package com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.RideRequest;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.DistanceService;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculatedDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());
        return distance* RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
