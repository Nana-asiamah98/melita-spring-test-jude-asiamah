package com.ml.ordermicroservice.exceptions;

public class EmptyListException extends Exception {
    public EmptyListException(String errorMessage) {
        super(errorMessage);
    }
}
