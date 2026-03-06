package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.exception;

public class DatabaseException extends QuantityMeasurementException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DatabaseException connectionFailed(String details, Throwable cause) {
        return new DatabaseException("Database connection failed: " + details, cause);
    }

    public static DatabaseException queryFailed(String details, Throwable cause) {
        return new DatabaseException("Database query failed: " + details, cause);
    }

    public static DatabaseException transactionFailed(String details, Throwable cause) {
        return new DatabaseException("Database transaction failed: " + details, cause);
    }
}
