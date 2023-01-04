package com.works.services;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    final ProductRepository productRepository;
    final Tracer tracer;
    final RestTemplate restTemplate;

    public ResponseEntity save(Product product) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", productRepository.save(product));
        return new ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity list() {
        // int i = 1 / 0;
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", productRepository.findAll());
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity findID( Long pid ) {
        Optional<Product> optionalProduct = productRepository.findById(pid);
        if (optionalProduct.isPresent()) {
            return new ResponseEntity(true, HttpStatus.OK);
        }else {
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "defaultMethod")
    public ResponseEntity allProd() {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        String url = "http://dummyjson.com/products";
        String data = restTemplate.getForObject(url, String.class);
        hm.put("result", data);
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity defaultMethod() {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", false);
        hm.put("error", "Dummyjson Server DOWN");
        return new ResponseEntity(hm, HttpStatus.NOT_FOUND);
    }
}
