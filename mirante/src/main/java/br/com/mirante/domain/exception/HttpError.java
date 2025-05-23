package br.com.mirante.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class HttpError {
    private int status;
    private String error;
    private String message;
    private String path;
    private Instant timestamp = Instant.now();
    
    public HttpError(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}

