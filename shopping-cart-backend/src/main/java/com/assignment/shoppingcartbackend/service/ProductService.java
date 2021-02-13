package com.assignment.shoppingcartbackend.service;

import com.assignment.shoppingcartbackend.dto.ProductDetails;
import com.assignment.shoppingcartbackend.dto.ProductPrice;
import com.assignment.shoppingcartbackend.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDetails> searchProducts(String keyword);

    List<ProductDetails> getProducts();

    Double calculatePrice(Integer productId, String type, Integer qty);

    ProductPrice getProductPrices(Integer productId, Integer productCount);
}
