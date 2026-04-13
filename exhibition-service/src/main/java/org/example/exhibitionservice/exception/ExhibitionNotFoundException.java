package org.example.exhibitionservice.exception;

public class ExhibitionNotFoundException  extends RuntimeException {
    public ExhibitionNotFoundException (String message) {
        super(message);
    }
}
