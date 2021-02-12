package com.assignment.shoppingcartbackend.service;

import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchProducts(String keyword);

    List<Product> getProducts();

    Double calculatePrice(Integer productId, Integer cartons, Integer units);

    ProductPrice getProductPrices(Integer productId, Integer productCount);
}
