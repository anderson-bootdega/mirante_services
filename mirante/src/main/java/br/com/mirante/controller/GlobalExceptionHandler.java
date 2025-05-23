package br.com.mirante.controller;

import br.com.mirante.domain.exception.BusinessException;
import br.com.mirante.domain.exception.HttpError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
  /*
  * se alguma validação falhar erro 400
  * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining("; "));
        
        HttpError error = new HttpError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    /*
    * se o erro for 404
    * */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpError> handleNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
        HttpError error = new HttpError(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    /*esse trecho vai tratar qualer exceção não tratada
    * */
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpError> handleAll(
            Exception ex,
            HttpServletRequest request
    ) {
        /*aqui é  log completo do stacktrace
        * */
        HttpError error = new HttpError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<HttpError> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        HttpError error = new HttpError(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de negócio",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
  
}
