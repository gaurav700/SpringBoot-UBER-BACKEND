package com.UBERAPP.UBER_BACKEND_PROJECT.services;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.RideRequest;

public interface RideRequestService {
    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
