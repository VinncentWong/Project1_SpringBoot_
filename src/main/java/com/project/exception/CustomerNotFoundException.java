package com.project.exception;

public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(){
        super();
    }

    public CustomerNotFoundException(String msg){
        super(msg);
    }
}
