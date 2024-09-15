package com.UBERAPP.UBER_BACKEND_PROJECT.services;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.WalletTransactionDTO;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransactionDTO walletTransactionDTO);
}
