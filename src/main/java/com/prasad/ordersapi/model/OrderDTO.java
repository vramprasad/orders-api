package com.prasad.ordersapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    //private String orderDescription;
    private List<CartItem> cartItems;
    private long customerId;
}
