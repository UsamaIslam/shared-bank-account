package com.upwork.assessment.controller.bank;

import com.upwork.assessment.exception.ResourceNotFoundException;
import com.upwork.assessment.model.BankAccounts;
import com.upwork.assessment.model.Users;
import com.upwork.assessment.repository.BankAccountRepository;
import com.upwork.assessment.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class StaffController {

  @Autowired
  BankAccountRepository bankAccountRepository;

  @Autowired
  private UsersRepository usersRepository;

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

  @PostMapping("/create-bank-accounts")
  public ResponseEntity<BankAccounts> createBankAccount(@RequestBody BankAccounts bankAccounts) {
    BankAccounts _bankAccounts = bankAccountRepository.save(bankAccounts);
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

  @GetMapping("/users")
  public ResponseEntity<List<Users>> getAllUsers() {

    List<Users> users = new ArrayList<Users>(usersRepository.findAll());

    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/bank-accounts/{bankAccountId}/users")
  public ResponseEntity<List<Users>> getAllUsersByBankAccountId(@PathVariable(value = "bankAccountId") Long bankAccountId) {
    if (!bankAccountRepository.existsById(bankAccountId)) {
      throw new ResourceNotFoundException("Not found Bank Accountwith id = " + bankAccountId);
    }

    List<Users> users = usersRepository.findByBankAccounts_Id(bankAccountId);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<Users> getUsersById(@PathVariable(value = "id") Long id) {
    Users user = usersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Users with id = " + id));

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/users/{userId}/bank-accounts")
  public ResponseEntity<List<BankAccounts>> getAllBankAccountsByUserId(@PathVariable(value = "userId") Long userId) {
    if (!usersRepository.existsById(userId)) {
      throw new ResourceNotFoundException("Not found Users  with id = " + userId);
    }

    List<BankAccounts> bankAccounts = bankAccountRepository.findBankAccountsByUsers_Id(userId);
    return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
  }

  @PostMapping("/bank-accounts/{bankAccountId}/users")
  public ResponseEntity<Users> addUser(@PathVariable(value = "bankAccountId") Long bankAccountId, @RequestBody Users tagRequest) {
    Users user = bankAccountRepository.findById(bankAccountId).map(tutorial -> {
      long userId = tagRequest.getId();

      // user is existed
      if (userId != 0L) {
        Users _tag = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Users with id = " + userId));
        tutorial.addUserToBankAccount(_tag);
        bankAccountRepository.save(tutorial);
        return _tag;
      }

      // add and create new Users
      tutorial.addUserToBankAccount(tagRequest);
      return usersRepository.save(tagRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Bank Accountwith id = " + bankAccountId));

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<Users> updateUser(@PathVariable("id") long id, @RequestBody Users userRequest) {
    Users user = usersRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserId " + id + "not found"));

    user.setUsername(userRequest.getUsername());

    return new ResponseEntity<>(usersRepository.save(user), HttpStatus.OK);
  }

  @DeleteMapping("/bank-accounts/{bankAccountId}/users/{userId}")
  public ResponseEntity<HttpStatus> deleteUserFromBankAccount(@PathVariable(value = "bankAccountId") Long bankAccountId, @PathVariable(value = "userId") Long userId) {
    BankAccounts bankAccounts = bankAccountRepository.findById(bankAccountId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Bank Accountwith id = " + bankAccountId));

    bankAccounts.removeUserFromBankAccount(userId);
    bankAccountRepository.save(bankAccounts);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
    usersRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
