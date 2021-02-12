package com.assignment.shoppingcartbackend.repository;

import com.assignment.shoppingcartbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductsByProductNameContains(String productName);
}
