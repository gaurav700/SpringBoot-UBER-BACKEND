package com.UBERAPP.UBER_BACKEND_PROJECT.strategies;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.PaymentMethod;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.PaymentStrategyCOD;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl.PaymentStrategyWallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final PaymentStrategyWallet paymentStrategyWallet;
    private final PaymentStrategyCOD paymentStrategyCOD;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod)
        {
            case WALLET ->  paymentStrategyWallet;
            case CASH ->  paymentStrategyCOD;
        };
    }
}
