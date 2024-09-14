package com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.RideRequest;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.DriverRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearbyTopRatedDriver(rideRequest.getPickUpLocation());
    }
}
