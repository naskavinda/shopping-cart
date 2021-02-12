package com.assignment.shoppingcartbackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductPrice {

    private int productId;
    private String productName;
    private List<PriceModel> priceModel;

}
