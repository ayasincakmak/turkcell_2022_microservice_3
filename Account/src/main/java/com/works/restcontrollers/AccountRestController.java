package com.works.restcontrollers;

import com.works.entities.Account;
import com.works.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account account) {
        return accountService.register(account);
    }

}