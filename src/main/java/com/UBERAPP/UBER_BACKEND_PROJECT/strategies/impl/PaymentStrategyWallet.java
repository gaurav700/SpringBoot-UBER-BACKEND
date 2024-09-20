package com.UBERAPP.UBER_BACKEND_PROJECT.strategies.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Driver;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Payment;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Rider;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.PaymentStatus;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.TransactionMethod;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.PaymentRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.PaymentService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletService;
import com.UBERAPP.UBER_BACKEND_PROJECT.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyWallet implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();
        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);
        double driversCut = payment.getAmount() * ( 1-PLATFORM_COMMISSION);
        walletService.addMoneyToWallet(driver.getUser(), driversCut, null, payment.getRide(), TransactionMethod.RIDE);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
