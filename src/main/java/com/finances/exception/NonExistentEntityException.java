package com.finances.exception;

public class NonExistentEntityException extends RuntimeException{
    public NonExistentEntityException(String s) {
        super(s);
    }
}
