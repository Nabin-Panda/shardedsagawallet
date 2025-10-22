package com.shardedsagawallet.shardedsagawallet.repositories;

import com.shardedsagawallet.shardedsagawallet.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
