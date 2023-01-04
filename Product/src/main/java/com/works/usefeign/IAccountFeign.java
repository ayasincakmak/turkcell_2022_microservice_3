package com.works.usefeign;

import com.works.props.Account;
import com.works.props.AccountRegister;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "account")
public interface IAccountFeign {

    @PostMapping("/account/register")
    AccountRegister register(Account account);

}
