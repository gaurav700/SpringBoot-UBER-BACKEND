package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Wallet;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    @Override
    public Wallet addMoneyToWallet(Long userId, Double amount) {
        return null;
    }

    @Override
    public void withdrawALlMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return null;
    }

    @Override
    public Wallet createNewWallet(User user) {
        return null;
    }
}
