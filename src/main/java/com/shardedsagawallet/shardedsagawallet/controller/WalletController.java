package com.shardedsagawallet.shardedsagawallet.controller;

import com.shardedsagawallet.shardedsagawallet.dto.CreateWalletDto;
import com.shardedsagawallet.shardedsagawallet.entities.Wallet;
import com.shardedsagawallet.shardedsagawallet.service.saga.WalletService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wallet")
@Data
@RequiredArgsConstructor
public class WalletController {
    private  final WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody CreateWalletDto createWalletdto){
        Wallet wallet = walletService.createWallet(createWalletdto.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletByUserId(@PathVariable Long userId){
        Wallet wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(wallet);
    }
}
