package com.UBERAPP.UBER_BACKEND_PROJECT.dto;

import com.UBERAPP.UBER_BACKEND_PROJECT.entities.WalletTransaction;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private Long id;
    private UserDTO user;
    private Double balance;
    private List<WalletTransaction> transactions;
}
