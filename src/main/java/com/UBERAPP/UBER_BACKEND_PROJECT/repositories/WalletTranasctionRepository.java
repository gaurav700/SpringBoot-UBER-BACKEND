package com.UBERAPP.UBER_BACKEND_PROJECT.repositories;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTranasctionRepository extends JpaRepository<WalletTransaction, Long> {
}
