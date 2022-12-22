package com.upwork.assessment.repository;

import com.upwork.assessment.model.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccounts, Long> {
  List<BankAccounts> findByActive(boolean active);

  List<BankAccounts> findByTitleContaining(String title);

  List<BankAccounts> findBankAccountsByUsers_Id(Long userId);

//  List<BankAccounts> findByUsers_Id(Long id);
}
