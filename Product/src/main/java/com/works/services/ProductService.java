package com.works.services;

import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository productRepository;

    public ResponseEntity save(Product product) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", productRepository.save(product));
        return new ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity list() {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", productRepository.findAll());
        return new ResponseEntity(hm, HttpStatus.OK);
    }
}
