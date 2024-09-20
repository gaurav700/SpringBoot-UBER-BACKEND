package com.UBERAPP.UBER_BACKEND_PROJECT.strategies;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Payment;

public interface PaymentStrategy {
    static final Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);
}
