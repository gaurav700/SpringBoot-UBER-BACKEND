package com.UBERAPP.UBER_BACKEND_PROJECT.services.impl;

import com.UBERAPP.UBER_BACKEND_PROJECT.dto.RideDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.WalletDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.dto.WalletTransactionDTO;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Wallet;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.WalletTransaction;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.TransactionMethod;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.TransactionType;
import com.UBERAPP.UBER_BACKEND_PROJECT.exceptions.ResourceNotFoundException;
import com.UBERAPP.UBER_BACKEND_PROJECT.repositories.WalletRepository;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletService;
import com.UBERAPP.UBER_BACKEND_PROJECT.services.WalletTransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod  transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod  transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();
        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawALlMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()->
                new ResourceNotFoundException("Wallet with this id is not found "+ walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("Wallet not found for this user :"+user.getName()));
    }
}
