package com.upwork.assessment.controller.bank;

import com.upwork.assessment.exception.ResourceNotFoundException;
import com.upwork.assessment.model.BankAccounts;
import com.upwork.assessment.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StaffController {

  @Autowired
  BankAccountRepository bankAccountRepository;

  @GetMapping("/bank-accounts")
  public ResponseEntity<List<BankAccounts>> getAllBankAccounts(@RequestParam(required = false) String title) {
    List<BankAccounts> bankAccounts = new ArrayList<BankAccounts>();

    if (title == null)
      bankAccounts.addAll(bankAccountRepository.findAll());
    else
      bankAccounts.addAll(bankAccountRepository.findByTitleContaining(title));

    if (bankAccounts.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
  }

  @GetMapping("/bank-accounts/{id}")
  public ResponseEntity<BankAccounts> getBankAccountById(@PathVariable("id") long id) {
    BankAccounts bankAccounts = bankAccountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Bank Accountwith id = " + id));

    return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
  }

  @PostMapping("/bank-accounts")
  public ResponseEntity<BankAccounts> createBankAccount(@RequestBody BankAccounts bankAccounts) {
    BankAccounts _bankAccounts = bankAccountRepository.save(new BankAccounts(bankAccounts.getTitle(), bankAccounts.getAccountNumber(), true));
    return new ResponseEntity<>(_bankAccounts, HttpStatus.CREATED);
  }

  @PutMapping("/bank-accounts/{id}")
  public ResponseEntity<BankAccounts> updateBankAccount(@PathVariable("id") long id, @RequestBody BankAccounts bankAccounts) {
    BankAccounts _bankAccounts = bankAccountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Bank Accountwith id = " + id));

    _bankAccounts.setTitle(bankAccounts.getTitle());
    _bankAccounts.setAccountNumber(bankAccounts.getAccountNumber());
    _bankAccounts.setActive(bankAccounts.isActive());
    
    return new ResponseEntity<>(bankAccountRepository.save(_bankAccounts), HttpStatus.OK);
  }

  @DeleteMapping("/bank-accounts/{id}")
  public ResponseEntity<HttpStatus> deleteBankAccount(@PathVariable("id") long id) {
    bankAccountRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/bank-accounts")
  public ResponseEntity<HttpStatus> deleteAllBankAccounts() {
    bankAccountRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/bank-accounts/active")
  public ResponseEntity<List<BankAccounts>> findByPublished() {
    List<BankAccounts> bankAccounts = bankAccountRepository.findByActive(true);

    if (bankAccounts.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
  }
}
