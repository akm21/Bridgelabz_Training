package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.exception;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message,Throwable cause) {
        super(message, cause);
    }
}
