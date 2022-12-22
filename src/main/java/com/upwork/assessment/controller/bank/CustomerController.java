package com.upwork.assessment.controller.bank;

import java.util.ArrayList;
import java.util.List;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import com.upwork.assessment.config.jwt.MyJwtUser;
import com.upwork.assessment.exception.ResourceNotFoundException;
import com.upwork.assessment.model.BankAccounts;
import com.upwork.assessment.model.Users;
import com.upwork.assessment.repository.BankAccountRepository;
import com.upwork.assessment.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user/api")
public class CustomerController {

  @Autowired
  private BankAccountRepository bankAccountRepository;

  @Autowired
  private UsersRepository usersRepository;
//((JwtAuthenticationToken) authentication).getTokenAttributes().get("myuser")

  @RequestMapping(value="/get-my-account-details", method=RequestMethod.GET)
  public List<BankAccounts> GetCustomerByAccNum(Authentication authentication
  ) throws ResourceNotFoundException
  {

    LinkedTreeMap<String,String> mymap = (LinkedTreeMap<String, String>) (((JwtAuthenticationToken) authentication).getTokenAttributes().get("myuser"));
    return bankAccountRepository.findBankAccountsByUsers_Username(mymap.get("username"));
  }
}
