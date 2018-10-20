package com.toxin.shorten.exception;

public class ValidException extends RuntimeException {

    public ValidException() {
        super("LINK NOT VALID");
    }

}
