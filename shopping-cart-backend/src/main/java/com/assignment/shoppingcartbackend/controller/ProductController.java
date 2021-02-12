package com.assignment.shoppingcartbackend.controller;

import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.model.Product;
import com.assignment.shoppingcartbackend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        try {
            return ResponseEntity.ok(productService.getProducts());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<Product>> searchProducts(@PathVariable String keyword) {
        try {
            return ResponseEntity.ok(productService.searchProducts(keyword));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{productId}/{cartons}/{units}")
    public ResponseEntity<Double> calculatePrice(@PathVariable Integer productId, @PathVariable Integer units,
                                                 @PathVariable Integer cartons) {
        try {
            return ResponseEntity.ok(productService.calculatePrice(productId, cartons, units));
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
