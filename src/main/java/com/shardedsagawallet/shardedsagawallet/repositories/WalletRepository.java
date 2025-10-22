package com.shardedsagawallet.shardedsagawallet.repositories;

import com.shardedsagawallet.shardedsagawallet.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
     Optional<Wallet> findByUserId(Long userId);
}
