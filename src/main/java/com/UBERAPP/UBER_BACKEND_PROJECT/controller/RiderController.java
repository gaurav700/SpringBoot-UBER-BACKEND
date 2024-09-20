package com.UBERAPP.UBER_BACKEND_PROJECT.controller;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.*;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.DriverService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final DriverService driverService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDTO> requestRide(@RequestBody RideRequestDTO rideRequestDTO){
        return ResponseEntity.ok(riderService.requestRide(rideRequestDTO));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDTO> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDTO> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDTO>> getMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                                    @RequestParam(defaultValue = "10" ,required=false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffset, pageSize);
        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }

    @PostMapping("/rateDriver/{rideId}/rating")
    public ResponseEntity<DriverDTO> rateDriver(@PathVariable Long rideId,
                                              @PathVariable Integer rating){
        return ResponseEntity.ok(riderService.rateDriver(rideId, rating));
    }

}
