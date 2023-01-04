package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.props.Account;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRestController {

    final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity list() {
        return productService.list();
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Product product){
        return productService.save(product);
    }

    @PostMapping("/find/{pid}")
    public ResponseEntity find(@PathVariable Long pid) {
        return productService.findID(pid);
    }

    @GetMapping("/allProduct")
    public ResponseEntity allProduct() {
        return productService.allProd();
    }

    @PostMapping("/register")
    public Object register(@RequestBody Account account) {
        return productService.register(account);
    }

}
