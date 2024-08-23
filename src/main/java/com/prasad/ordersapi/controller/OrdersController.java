package com.prasad.ordersapi.controller;

import com.prasad.ordersapi.model.CartItem;
import com.prasad.ordersapi.model.Customer;
import com.prasad.ordersapi.model.Order;
import com.prasad.ordersapi.model.OrderDTO;
import com.prasad.ordersapi.repo.CartRepository;
import com.prasad.ordersapi.repo.OrderRepository;
import com.prasad.ordersapi.service.CalculateTotal;
import com.prasad.ordersapi.service.GetCustomerService;
import com.prasad.ordersapi.util.DateUtil;
import com.prasad.ordersapi.util.InvoiceNumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class OrdersController {

    @Autowired
    GetCustomerService customerService;

    @Autowired
    CalculateTotal calcService;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    CartRepository cartRepo;


    @GetMapping("/healthcheck")
    ResponseEntity<String> healthcheck() throws InterruptedException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        log.info("Inside OrdersController --> healthcheck");
        String responseText = "orders-api healthcheck @ " + timeStamp + " - All OK";
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(responseText.toString());
    }


    @GetMapping("/listAll")
    public List<Order> getAllOrders() {
        log.info("Inside OrdersController --> getAllOrders");
        return orderRepo.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable long id) {
        log.info("Inside OrdersController --> getOrder");
        return orderRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/placeOrder")
    public Order processOrder(@RequestBody OrderDTO orderDTO){
        log.info("Inside OrdersController --> processOrder");
        log.info("Payload : "+ orderDTO.toString());
        Order order = new Order();
        Customer c1 = new Customer();
        c1 = customerService.getCustomer(orderDTO.getCustomerId());
        order.setCustName(c1.getCustName());
        order.setCustEmail(c1.getCustEmail());
        log.info("Generating invoice number");
        order.setInvoiceNumber(InvoiceNumUtil.getInvoiceNumber());
        log.info("Attaching timestamp");
        List<CartItem> updatedCarts = new ArrayList<CartItem>();
        updatedCarts = calcService.updateCartDetails(orderDTO.getCartItems());
        order.setInvoiceDate(DateUtil.getCurrentDateTime());
        order.setTotalAmount(calcService.getTotal(updatedCarts));
        order.setCartItems(updatedCarts);
        log.info("Printing final list of items");
        for (CartItem tmp : order.getCartItems()) {
            log.info("Saving Cart item : " + tmp.toString());
            cartRepo.save(tmp);
        }
        log.info("Before saving Order ID : " + order.getId() + " processed successfully");
        orderRepo.save(order);
        log.info("After saving Order ID : " + order.getId() + " processed successfully");
        return order;
    }



}
