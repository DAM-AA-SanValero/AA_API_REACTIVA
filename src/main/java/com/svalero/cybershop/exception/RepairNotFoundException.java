package com.svalero.cybershop.exception;

public class RepairNotFoundException extends Exception{
    public RepairNotFoundException() {
        super("Repair not found");
    }
    public RepairNotFoundException(String message){
        super(message);
    }

}
