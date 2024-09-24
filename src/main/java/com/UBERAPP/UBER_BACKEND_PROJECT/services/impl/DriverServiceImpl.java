package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RiderDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.*;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.RideRequestStatus;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.RideStatus;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.DriverRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.RiderRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private  final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDTO cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel a ride as he is not accepted this ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("We cannot cancel the ride because the ride is ONGOING");
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);
        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not confirmed, Hence cannot be started, Status : " + ride.getRideStatus());
        }

        if(!ride.getOtp().equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid");
        }
        ride.setStartAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot end the ride as he not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Driver cannot end the ride as the ride is not ONGOING");
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);


        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request cannot be accepted, status is "+rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver is cannot accept ride due to unavailability");
        }
        Driver savedDriver = updateDriverAvailability(currentDriver, false);
        Ride ride = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(ride, RideDTO.class) ;
    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!ride.getDriver().equals(driver)){
            throw new RuntimeException("This ride doesn't belong to the current driver");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride is not ended yet so you cannot give rating till then");
        }

        return ratingService.rateRider(ride, rating);
    }

    @Override
    public DriverDTO getMyProfile() {
        Driver driver = getCurrentDriver();
        return modelMapper.map(driver, DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Driver currDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currDriver, pageRequest).map(
                ride-> modelMapper.map(ride, RideDTO.class)
        );
    }

    @Override
    public Driver getCurrentDriver(){
        User user = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Driver not associated with user with " +
                "id "+user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
