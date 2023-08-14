package com.svalero.cybershop.exception;

public class TechnicianNotFoundException extends Exception{
    public TechnicianNotFoundException() {
        super("Technician not found");
    }
    public TechnicianNotFoundException(String message){
        super(message);
    }

}
