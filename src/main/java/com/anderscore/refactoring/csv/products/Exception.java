package com.anderscore.refactoring.csv.products;

public class Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;


	public Exception(String message) {
        super(message);
    }

    public Exception(Throwable t) {
        super(t);
    }


    public Exception(final String message, final Throwable cause) {
        super(message, cause);
    }
}