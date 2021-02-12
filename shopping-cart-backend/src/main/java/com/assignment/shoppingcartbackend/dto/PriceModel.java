package com.assignment.shoppingcartbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceModel {

    private Integer totalUnits;
    private Double price;
}
