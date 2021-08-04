package com.uptc.exceptions;

 
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

/***
 * Controlador de excepciones
 */

@RestControllerAdvice
public class ApiExceptionHandler {
   
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            ResourceNotFoundException.class,
            org.springframework.security.core.userdetails.UsernameNotFoundException.class
    })
    public ErrorMessage notFoundRequest(Exception exception, WebRequest request) {
        return new ErrorMessage(exception, HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorMessage forbidden(ForbiddenException exception, WebRequest request) { 
        return new ErrorMessage(exception, HttpStatus.FORBIDDEN.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            InternalServerError.class,
            Exception.class
    })
    public ErrorMessage exception(Exception exception, WebRequest request) { // The error must be corrected
        return new ErrorMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            org.springframework.dao.DataIntegrityViolationException.class,
            org.springframework.dao.DuplicateKeyException.class,
            org.springframework.web.bind.MethodArgumentNotValidException.class,
            org.springframework.web.bind.support.WebExchangeBindException.class,
            org.springframework.web.bind.MissingServletRequestParameterException.class,
            org.springframework.web.server.ServerWebInputException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.HttpMediaTypeNotSupportedException.class
    })
    public ErrorMessage badRequest(Exception exception, WebRequest request) {
        return new ErrorMessage(exception, HttpStatus.BAD_REQUEST.value());
    }

}