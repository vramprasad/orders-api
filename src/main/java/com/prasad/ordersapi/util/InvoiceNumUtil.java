package com.prasad.ordersapi.util;

import java.security.SecureRandom;

public class InvoiceNumUtil {
    public static String getInvoiceNumber(){
        return (String.format("%08d", new SecureRandom().nextInt(10_000_000)));
    }
}
