package com.upwork.assessment.controller.bank;

import java.util.ArrayList;
import java.util.List;

import com.upwork.assessment.exception.ResourceNotFoundException;
import com.upwork.assessment.model.BankAccounts;
import com.upwork.assessment.model.Users;
import com.upwork.assessment.repository.BankAccountRepository;
import com.upwork.assessment.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/api")
public class CustomerController {

  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private UsersRepository usersRepository;
//((JwtAuthenticationToken) authentication).getTokenAttributes().get("myuser")

}
