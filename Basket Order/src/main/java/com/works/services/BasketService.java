package com.works.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.props.Account;
import com.works.props.AccountData;
import com.works.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasketService {

    final BasketRepository repository;
    final HttpServletRequest req;
    final DiscoveryClient discoveryClient;
    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    public ResponseEntity addBasket( Long pid ) {
        Map<String, String> hm = new LinkedHashMap<>();
        AccountData accountData = (AccountData) req.getSession().getAttribute("account");
        List<ServiceInstance> instances = discoveryClient.getInstances("product");
        ServiceInstance instance = instances.get(0);
        String baseUrl = instance.getUri().toString();
        baseUrl = baseUrl + "/product/find/"+pid;
        System.out.println( baseUrl );

        Account account = new Account("ali@mail.com", "12345");
        String jsonObj = null;
        try {
            jsonObj = objectMapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(jsonObj, headers);

        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(baseUrl, httpEntity, Boolean.class);
        System.out.println( responseEntity.getBody() );
        System.out.println( accountData.getResult().getAid() );
        return new ResponseEntity(hm, HttpStatus.OK);
    }

}
