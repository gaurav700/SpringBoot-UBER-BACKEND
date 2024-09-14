package com.UBERAPP.UBER_BACKEND_PROJECT.strategies;

import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RiderFareDefaultFareCalculationStrategy riderFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating){
        if(riderRating>=4.8){
            return driverMatchingHighestRatedDriverStrategy;
        }else{
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        // peak hours 6Pm to 9Pm
        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(21,0);
        LocalTime currTime = LocalTime.now();

        boolean isSurgeTime = currTime.isAfter(surgeStartTime) && currTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return rideFareSurgePricingFareCalculationStrategy;
        }else{
            return riderFareDefaultFareCalculationStrategy;
        }
    }

}
