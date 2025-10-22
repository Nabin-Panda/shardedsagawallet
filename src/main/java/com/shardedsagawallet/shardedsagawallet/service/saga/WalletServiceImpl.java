package com.shardedsagawallet.shardedsagawallet.service.saga;

import com.shardedsagawallet.shardedsagawallet.entities.User;
import com.shardedsagawallet.shardedsagawallet.entities.Wallet;
import com.shardedsagawallet.shardedsagawallet.repositories.UserRepository;
import com.shardedsagawallet.shardedsagawallet.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Wallet createWallet(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        if (walletRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Wallet already exists for user: " + userId);
        }
        Wallet wallet = Wallet.builder()
                .userId(userId)
                .isActive(true)
                .balance(BigDecimal.ZERO)
                .build();
        return walletRepository.save(wallet);
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        return null;
    }

    @Override
    public Wallet getWalletByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return walletRepository.findByUserId((userId)).orElseThrow(()->new RuntimeException("Wallet not found with this user id"));
    }

    @Override
    public Wallet creditWallet(Long userId, BigDecimal amount) {
        return null;
    }

    @Override
    public Wallet debitWallet(Long userId, BigDecimal amount) {
        return null;
    }
}
