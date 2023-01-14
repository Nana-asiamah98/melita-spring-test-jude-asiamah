package com.ml.ordermicroservice.exceptions;

public class OrderNotExistsException extends  RuntimeException{
    public OrderNotExistsException(){
        super("Order does no exist");
    }
}
