package com.prasad.ordersapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    private long id;
    private String productName;
    private int productAvlQty;
    private float productPrice;
    private float discPercent;
}