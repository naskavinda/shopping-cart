package com.assignment.shoppingcartbackend.repository;

import com.assignment.shoppingcartbackend.entity.Carton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartonRepository extends JpaRepository<Carton, Integer> {

    Carton findCartonByProductId(int productId);

}
