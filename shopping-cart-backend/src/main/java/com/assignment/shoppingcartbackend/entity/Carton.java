package com.assignment.shoppingcartbackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Carton {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Integer unitsPerCarton;
    private Double price;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
}
