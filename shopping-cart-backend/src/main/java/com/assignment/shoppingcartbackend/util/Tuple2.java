package com.assignment.shoppingcartbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tuple2<K, V> {

    private K first;
    private V second;

}
