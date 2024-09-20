package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.WalletTransactionDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.WalletTransaction;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.WalletTranasctionRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTranasctionRepository walletTranasctionRepository;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTranasctionRepository.save(walletTransaction);
    }
}
