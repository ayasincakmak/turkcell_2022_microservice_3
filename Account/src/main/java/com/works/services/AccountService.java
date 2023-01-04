package com.works.services;

import com.works.entities.Account;
import com.works.repositories.AccountRepository;
import com.works.utils.REnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    @Value("${sample.data}")
    private String sample;

    final AccountRepository accountRepository;
    final TinkEncDec tinkEncDec;
    final HttpServletRequest req;
    final DiscoveryClient discoveryClient;

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


    public ResponseEntity login( Account account ) {
        log.info("Login Action");
        Optional<Account> optionalAccount = accountRepository.findByEmailEqualsIgnoreCase(account.getEmail());
        Map<REnum, Object> hm = new LinkedHashMap<>();
        if (optionalAccount.isPresent() ) {
            Account ac = optionalAccount.get();
            String dbPass = tinkEncDec.decrypt(ac.getPassword());
            if (dbPass.equals(account.getPassword())) {
                hm.put(REnum.status, true);
                hm.put(REnum.result, ac);
                req.getSession().setAttribute("account", ac);

                // List<ServiceInstance> list = discoveryClient.getInstances("ACCOUNT");
                // ServiceInstance instance = list.get(0);
                InetAddress iAddr = null;
                try {
                    iAddr = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String host = iAddr.getHostAddress();
                int port = req.getLocalPort();

                String server = host;
                String sessionID = req.getSession().getId();
                hm.put(REnum.sessionID, sessionID);
                hm.put(REnum.server, server);
                System.out.println("sample: " + sample);
                return new ResponseEntity(hm, HttpStatus.OK);
            }
        }
           hm.put(REnum.status, false);
           hm.put(REnum.message, "Email or Password Fail");
           return new ResponseEntity(hm, HttpStatus.UNAUTHORIZED);
    }



}
