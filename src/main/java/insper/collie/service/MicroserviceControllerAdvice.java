package insper.collie.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import insper.collie.service.exceptions.MicroserviceNotFoundException;


@ControllerAdvice
public class MicroserviceControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler({MicroserviceNotFoundException.class})
    private ResponseEntity<String> notFoundHandler(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
