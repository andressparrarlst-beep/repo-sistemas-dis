package com.uptc.uptc.exception.created;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailTakenException extends RuntimeException{
    public EmailTakenException() { 
        super("Email taken"); 
    }
}
