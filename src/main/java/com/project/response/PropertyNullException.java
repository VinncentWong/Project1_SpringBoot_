package com.project.response;

public class PropertyNullException extends Exception{
    public PropertyNullException(){
        super();
    }

    public PropertyNullException(String msg){
        super(msg);
    }
}
