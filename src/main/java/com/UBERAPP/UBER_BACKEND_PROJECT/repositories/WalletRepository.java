package com.UBERAPP.UBER_BACKEND_PROJECT.repositories;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.User;
import com.UBERAPP.UBER_BACKEND_PROJECT.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
