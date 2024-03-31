package me.dio.sdw24.adapters.in;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ChampionNotFoundException.class)
    public ResponseEntity<ApiError> handlerDomainException(ChampionNotFoundException domainError) {
        return ResponseEntity.unprocessableEntity().body(new ApiError(domainError.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handlerDomainException(Exception unexpectedError) {
        logger.error("",unexpectedError);
        return ResponseEntity
                .internalServerError()
                .body(new ApiError("Ops! ocorreu um erro inesperado!"));
    }

    
    public record ApiError(String message) {
    }
}
