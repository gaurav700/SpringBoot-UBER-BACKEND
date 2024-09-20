package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.DriverDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RiderDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rating;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rider;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.DriverRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.RatingRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.RiderRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper  modelMapper;

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found for the id :"+ride.getId()));

        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeException("Driver has already been rated , cannot rate again");
        }

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream().mapToDouble(rating1-> rating1.getDriverRating())
                .average().orElse(0.0);
        driver.setRating(newRating);

        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDTO.class);
    }

    @Override
    public RiderDTO rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found for the id :"+ride.getId()));

        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeException("Rider has already been rated , cannot rate again");
        }

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream().mapToDouble(rating1-> rating1.getRiderRating())
                .average().orElse(0.0);
        rider.setRating(newRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDTO.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();

        ratingRepository.save(rating);
    }
}
