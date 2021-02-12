package com.assignment.shoppingcartbackend.model;

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

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Product product;
}
