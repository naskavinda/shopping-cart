package com.assignment.shoppingcartbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDetails {

    private int id;
    private String productName;
    private Integer unitsPerCarton;
}
