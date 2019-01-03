package com.aakashsharan.app.exceptions;

public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -318255061909111466L;

    public UserServiceException(String message){
        super(message);
    }
}
