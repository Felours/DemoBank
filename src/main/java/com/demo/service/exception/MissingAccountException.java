package com.demo.service.exception;

/**
 * Represents the case in which no account was found.
 */
public class MissingAccountException extends RuntimeException {

    public MissingAccountException(String message){

        super(message);

    }

}
