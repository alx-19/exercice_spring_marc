package com.alex.buildindingAndco.exception;

public class UnknownResourceException extends RuntimeException{

    public UnknownResourceException(){
        super("Unkonwn resource.");
    }

    public UnknownResourceException(String message){
        super(message);
    }

}
