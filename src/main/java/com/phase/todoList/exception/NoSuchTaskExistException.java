package com.phase.todoList.exception;

public class NoSuchTaskExistException extends RuntimeException{
    private String message;

    public NoSuchTaskExistException(){};
    public NoSuchTaskExistException(String message) {
        super(message);
        this.message = message;
    }
}
