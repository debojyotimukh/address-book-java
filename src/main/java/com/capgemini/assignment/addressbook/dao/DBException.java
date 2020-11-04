package com.capgemini.assignment.addressbook.dao;

public class DBException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -154491894122914988L;

    public DBException() {
    }

    public DBException(String message) {
        super(message);
    }

}
