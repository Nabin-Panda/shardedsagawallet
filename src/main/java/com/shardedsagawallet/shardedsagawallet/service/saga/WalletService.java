package com.shardedsagawallet.shardedsagawallet.service.saga;

import com.shardedsagawallet.shardedsagawallet.entities.Wallet;

import java.math.BigDecimal;

public interface WalletService {
    public Wallet createWallet(Long userId);
    public BigDecimal getBalance(Long userId);
    public Wallet getWalletByUserId(Long userId);
    public Wallet creditWallet(Long userId , BigDecimal amount);
    public Wallet debitWallet(Long userId, BigDecimal amount);
}
