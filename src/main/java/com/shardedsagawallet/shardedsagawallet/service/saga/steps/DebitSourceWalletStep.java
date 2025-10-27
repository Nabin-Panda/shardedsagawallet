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
public class DebitSourceWalletStep implements SagaStepInterface {

    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public boolean execute(SagaContext context) {
        Long fromUserId = context.getLong("fromUserId");
        BigDecimal amount = context.getBigDecimal("amount");

        log.info("Executing debit step - UserId: {}, Amount: {}", fromUserId, amount);

        Wallet wallet = walletRepository.findByUserIdWithLock(fromUserId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + fromUserId));

        log.info("Wallet fetched - WalletId: {}, Current Balance: {}", wallet.getId(), wallet.getBalance());

        BigDecimal oldBalance = wallet.getBalance();
        wallet.debit(amount);
        walletRepository.save(wallet);

        log.info("Successfully debited wallet - WalletId: {}, Amount: {}, Old Balance: {}, New Balance: {}",
                wallet.getId(), amount, oldBalance, wallet.getBalance());

        return true;
    }

    @Override
    @Transactional
    public boolean compensate(SagaContext context) {
        Long fromUserId = context.getLong("fromUserId");
        BigDecimal amount = context.getBigDecimal("amount");

        log.info("Compensating debit (crediting back) - UserId: {}, Amount: {}", fromUserId, amount);

        Wallet wallet = walletRepository.findByUserIdWithLock(fromUserId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + fromUserId));

        log.info("Wallet fetched for compensation - WalletId: {}, Current Balance: {}",
                wallet.getId(), wallet.getBalance());

        BigDecimal oldBalance = wallet.getBalance();
        wallet.credit(amount);
        walletRepository.save(wallet);

        log.info("Successfully compensated (credited back) - WalletId: {}, Amount: {}, Old Balance: {}, New Balance: {}",
                wallet.getId(), amount, oldBalance, wallet.getBalance());

        return true;
    }

    @Override
    public String getStepName() {
        return StepsNames.DEBIT_SOURCE_WALLET.toString();
    }
}