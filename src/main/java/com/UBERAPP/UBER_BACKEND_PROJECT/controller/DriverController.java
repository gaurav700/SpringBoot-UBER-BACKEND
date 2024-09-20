package com.UBERAPP.UBER_BACKEND_PROJECT.controller;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideStartDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDTO> startRide(@PathVariable Long rideRequestId, @RequestBody RideStartDTO rideStartDTO){
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDTO.getOtp()));
    }

    @PostMapping("/endRide/{rideRequestId}")
    public ResponseEntity<RideDTO> endRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.endRide(rideRequestId));
    }

}
