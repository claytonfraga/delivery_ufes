package com.ufes.delivery.exception;

public class OperacaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperacaoInvalidaException( String message ) {
		super( message );
	}
}
