package com.shardedsagawallet.shardedsagawallet.service.saga.steps;

import com.shardedsagawallet.shardedsagawallet.entities.Wallet;
import com.shardedsagawallet.shardedsagawallet.repositories.WalletRepository;
import com.shardedsagawallet.shardedsagawallet.service.saga.SagaContext;
import com.shardedsagawallet.shardedsagawallet.service.saga.SagaStepInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditDestinationWalletStep implements SagaStepInterface {
    private final WalletRepository walletRepository;


    @Override
    @Transactional
    public boolean execute(SagaContext context) {
        // Step 1: Get the destination wallet id from the context
        Long toUserId = context.getLong("toUserId");
        BigDecimal amount = context.getBigDecimal("amount");

        log.info("Crediting destination wallet of user {} with amount {}", toUserId, amount);

        // Step 2: Fetch the destination wallet from the database with a lock
        Wallet wallet = walletRepository.findByUserIdWithLock(toUserId).orElseThrow(()->new RuntimeException("No Wallet found"));
        log.info("Wallet fetched with balance for credit {}", wallet.getBalance());

        // Step 3: Credit the destination wallet
        wallet.credit(amount);
        Wallet updatedWallet = walletRepository.save(wallet);
        return true;
        // Once the context is updated in memory, we need to update the context in the database
    }

    @Override
    @Transactional
    public boolean compensate(SagaContext context) {
        // Step 1: Get the destination wallet id from the context
        Long toUserId = context.getLong("toUserId");
        BigDecimal amount = context.getBigDecimal("amount");

        log.info("compensating credit destination wallet of user {} with amount {}", toUserId, amount);

        // Step 2: Fetch the destination wallet from the database with a lock
        Wallet wallet = walletRepository.findByUserIdWithLock(toUserId).orElseThrow(()->new RuntimeException("No Wallet found"));
        log.info("Wallet fetched with balance for debit compensation {}", wallet.getBalance());

        // Step 3: Credit the destination wallet
        wallet.debit(amount);
        walletRepository.save(wallet);
        // Once the context is updated in memory, we need to update the context in the database
        return true;
    }

    @Override
    public String getStepName() {
        return StepsNames.CREDIT_DESTINATION_WALLET.toString();
    }
}
