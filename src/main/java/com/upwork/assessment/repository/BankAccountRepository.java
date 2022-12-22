package com.upwork.assessment.repository;

import com.upwork.assessment.model.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccounts, Long> {
  List<BankAccounts> findByActive(boolean active);

  List<BankAccounts> findByTitleContaining(String title);

  List<BankAccounts> findBankAccountsByUsers_Id(Long userId);

  List<BankAccounts> findBankAccountsByUsers_Username(String username);


//  List<BankAccounts> findByUsers_Id(Long id);
}
