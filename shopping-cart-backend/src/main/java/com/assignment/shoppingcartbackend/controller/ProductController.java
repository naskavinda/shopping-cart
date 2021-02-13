package com.assignment.shoppingcartbackend.controller;

import com.assignment.shoppingcartbackend.dto.ProductDetails;
import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.entity.Product;
import com.assignment.shoppingcartbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDetails>> getProducts() {
        try {
            return ResponseEntity.ok(productService.getProducts());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<ProductDetails>> searchProducts(@PathVariable String keyword) {
        try {
            return ResponseEntity.ok(productService.searchProducts(keyword));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}/{type}/{qty}")
    public ResponseEntity<Double> calculatePrice(@PathVariable Integer productId, @PathVariable String type,
                                                 @PathVariable Integer qty) {
        try {
            return ResponseEntity.ok(productService.calculatePrice(productId, type, qty));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prices/{productId}")
    public ResponseEntity<ProductPrice> getProductPrices(@PathVariable Integer productId,
                                                         @RequestParam(name = "productCount", required = false, defaultValue = "50") Integer productCount) {
        try {
            return ResponseEntity.ok(productService.getProductPrices(productId, productCount));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
