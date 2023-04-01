package br.com.desafioattornatus.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();

	public ValidationError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidationError(Instant timestamp, Integer status, String error) {
		super(timestamp, status, error);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErros() {
		return erros;
	}

	public void addErros(String fieldName, String message) {
		this.erros.add(new FieldMessage(fieldName, message));
	}
	

}
