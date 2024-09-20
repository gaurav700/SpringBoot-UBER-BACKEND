package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideRequestDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RiderDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.*;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.RideRequestStatus;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.RideStatus;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.DriverRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.RideRequestRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.RiderRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.DriverService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RideService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RiderService;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.RideStrategyManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {
        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDTO, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest request = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

        // todo: send notifications to all the drivers about the ride request

        return modelMapper.map(request, RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider doesn't own the this ride with id :"+rideId);
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Rider cannot cancel the ride because the ride is "+ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);
        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public DriverDTO rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = ride.getDriver();
        Double currRating = driver.getRating();
        Double newRating = 10 * currRating;
        Double updatedRating = newRating / 11;
        driver.setRating(updatedRating);
        driverRepository.save(driver);
        return modelMapper.map(driver, DriverDTO.class);

    }

    @Override
    public RiderDTO getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider currRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currRider, pageRequest).map(
                ride-> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider(){
        // todo implement spring security
        return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException("Rider not found with id"));
    }

}
