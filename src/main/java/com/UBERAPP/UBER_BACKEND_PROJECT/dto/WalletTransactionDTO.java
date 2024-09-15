package com.UBERAPP.UBER_BACKEND_PROJECT.dto;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Ride;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.TransactionMethod;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.enums.TransactionType;

import lombok.*;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDTO {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private Ride ride;
    private String transactionId;
    private WalletDTO wallet;
    private LocalDateTime timeStamp;
}
