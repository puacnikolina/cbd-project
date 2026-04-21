package org.example.bookingservice.exception;
import org.springframework.http.HttpStatus;

public class BookingServiceException extends RuntimeException {
    private final HttpStatus status;
    public BookingServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
