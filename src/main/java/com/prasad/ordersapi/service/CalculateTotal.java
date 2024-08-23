package com.prasad.ordersapi.service;

import com.prasad.ordersapi.model.CartItem;
import com.prasad.ordersapi.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CalculateTotal {

    @Value("${productapi.url}")
    private String productAPIBaseURL;

    public List<CartItem> updateCartDetails(List<CartItem> cartItemList) {


        log.info("Inside CalculateTotal --> updateCartDetails");
        List<CartItem> updatedCarts = new ArrayList<CartItem>();
        //int itemCounter = 1;
        for (CartItem cart : cartItemList) {
            int productId = cart.getProductId();

            RestTemplate restTemplateProduct = new RestTemplate();
            String productAPIURL = productAPIBaseURL+productId;
            log.info("Invoking URL : "+productAPIURL);
            ResponseEntity<Product> responseProduct = restTemplateProduct.getForEntity(productAPIURL, Product.class);
            Product product1 = new Product(
                    responseProduct.getBody().getId(),
                    responseProduct.getBody().getProductName(),
                    responseProduct.getBody().getProductAvlQty(),
                    responseProduct.getBody().getProductPrice(),
                    responseProduct.getBody().getDiscPercent()
            );
            log.info("Product --> " + product1.toString());
            log.info("Cart -->  "+ cart.toString());

            // Create and update the cart with amount per item
            CartItem upCart = new CartItem();
            //upCart.setId(itemCounter);
            upCart.setProductId(cart.getProductId());
            upCart.setProductName(product1.getProductName());
            upCart.setQuantity(cart.getQuantity());
            upCart.setUnitPrice(product1.getProductPrice());
            upCart.setDiscPercent(product1.getDiscPercent());
            float discountP = (((100-product1.getDiscPercent())/100)*product1.getProductPrice());
            float finalPr = (cart.getQuantity()*discountP);
            upCart.setFinalPrice(finalPr);
            boolean add = updatedCarts.add(upCart);
            //itemCounter++;
        }
        log.info("Number of items in updated cart = " + updatedCarts.size());
        return updatedCarts;
    }

    public float getTotal(List<CartItem> cartItemList) {
        float totalAmount = 0.0f;
        for (CartItem cart : cartItemList) {
            totalAmount = totalAmount + cart.getFinalPrice();
        }
        return totalAmount;
    }
}
