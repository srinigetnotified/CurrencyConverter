package com.currencyconverter;

public class InvalidCurrencyException extends RuntimeException {
    InvalidCurrencyException(String message){
        super(message);
    }
}
