package com.pluralsight.concerttracker.service;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String model, long id) {
        super("No " + model + " found with id: " + id);
    }
}
