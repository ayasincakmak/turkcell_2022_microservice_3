package com.works.services;

import com.works.entities.Account;
import com.works.repositories.AccountRepository;
import com.works.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    final AccountRepository accountRepository;
    final TinkEncDec tinkEncDec;

    public ResponseEntity register(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByEmailEqualsIgnoreCase(account.getEmail());
        Map<REnum, Object> hm = new LinkedHashMap<>();
        if (optionalAccount.isPresent()) {
            hm.put(REnum.status, false);
            hm.put(REnum.errors, "Email use");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }else {
            String newPass = tinkEncDec.encrypt(account.getPassword());
            account.setPassword(newPass);
            System.out.println(newPass);
            accountRepository.save(account);
            hm.put(REnum.status, true);
            hm.put(REnum.result, account);
            return new ResponseEntity(hm, HttpStatus.OK);
        }
    }


}
