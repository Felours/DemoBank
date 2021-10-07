package com.demo.exception;

/**
 * Represents a case in which an element is null and should not be.
 */
public class IsNullException extends RuntimeException{

    public IsNullException(String message){
        super(message);
    }

}
