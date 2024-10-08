package ru.company.taskservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.company.taskservice.exception.InvalidCredentialsException;
import ru.company.taskservice.exception.TaskNotFoundException;
import ru.company.taskservice.exception.UserAlreadyExistException;
import ru.company.taskservice.exception.UserNotFoundException;


@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({UserNotFoundException.class, TaskNotFoundException.class})
    public ResponseEntity<String> handleWalletNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({InvalidCredentialsException.class, UserAlreadyExistException.class})
    public ResponseEntity<String> handleInsufficientFunds(Throwable ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnknownError(Throwable ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
