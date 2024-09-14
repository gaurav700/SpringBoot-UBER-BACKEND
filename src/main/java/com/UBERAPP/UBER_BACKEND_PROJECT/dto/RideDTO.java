package com.UBERAPP.UBER_BACKEND_PROJECT.dto;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.PaymentMethod;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class RideDTO {
    private Long id;
    private PointDTO pickUpLocation;
    private PointDTO dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDTO rider;
    private DriverDTO driver;
    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startAt;
    private LocalDateTime endedAt;
}
