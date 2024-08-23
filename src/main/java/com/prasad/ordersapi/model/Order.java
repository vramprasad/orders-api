package com.prasad.ordersapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "orders_gen", sequenceName = "seq_order",  initialValue = 7201, allocationSize = 1)
@Entity
@Table(name = "tbl_order")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_gen")
    private long id;

    private float totalAmount;
    private String invoiceNumber;
    private String invoiceDate;
    private String custName;
    private String custEmail;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = CartItem.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<CartItem> cartItems;


}