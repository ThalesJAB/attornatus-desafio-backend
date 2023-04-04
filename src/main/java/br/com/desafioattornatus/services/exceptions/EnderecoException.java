package br.com.desafioattornatus.services.exceptions;

public class EnderecoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EnderecoException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public EnderecoException(String message) {
		super(message);
		
	}

}
