package com.shardedsagawallet.shardedsagawallet.service.saga.steps;

import com.shardedsagawallet.shardedsagawallet.entities.Wallet;
import com.shardedsagawallet.shardedsagawallet.repositories.WalletRepository;
import com.shardedsagawallet.shardedsagawallet.service.saga.SagaContext;
import com.shardedsagawallet.shardedsagawallet.service.saga.SagaStepInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditDestinationWalletStep implements SagaStepInterface {
    private final WalletRepository walletRepository;


    @Override
    public boolean execute(SagaContext context) {
        // Step 1: Get the destination wallet id from the context
        Long toWalletId = context.getLong("toWalletId");
        BigDecimal amount = context.getBigDecimal("amount");

        log.info("Crediting destination wallet {} with amount {}", toWalletId, amount);

        // Step 2: Fetch the destination wallet from the database with a lock
        Wallet wallet = walletRepository.findById(toWalletId).orElseThrow(()->new RuntimeException("No Wallet found"));
        log.info("Wallet fetched with balance {}", wallet.getBalance());

        // Step 3: Credit the destination wallet
        wallet.credit(amount);
        walletRepository.save(wallet);
        // Once the context is updated in memory, we need to update the context in the database
        return false;
    }

    @Override
    public boolean compensate(SagaContext context) {
        return false;
    }

    @Override
    public String getStepName() {
        return "";
    }
}
