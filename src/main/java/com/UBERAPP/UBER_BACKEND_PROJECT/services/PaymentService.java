package com.UBERAPP.UBER_BACKEND_PROJECT.services;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Payment;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
