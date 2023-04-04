package br.com.desafioattornatus.resources.exceptions;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.desafioattornatus.services.exceptions.DataIntegrityViolationException;
import br.com.desafioattornatus.services.exceptions.EnderecoException;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		String error = "Object not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e,
			HttpServletRequest request) {
		String error = "Data integrity violation";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException e,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError error = new ValidationError(Instant.now(), status.value(), "Erro na validação dos campos");

		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addErros(x.getField(), x.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(error);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError error = new StandardError(Instant.now(), status.value(), "Bad Request", e.getMessage(),
				request.getRequestURI());

		Throwable mostSpecificCause = e.getMostSpecificCause();

		if (mostSpecificCause instanceof DateTimeParseException) {
			error.setError("Erro na validação dos campos");
			error.setMessage("Formato de data incorreto. Use o formato 'yyyy-MM-dd");

		}

		return ResponseEntity.status(status).body(error);
	}
	
	
	@ExceptionHandler(EnderecoException.class)
	public ResponseEntity<StandardError> enderecoError(EnderecoException e, HttpServletRequest request){
		

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError error = new StandardError(Instant.now(), status.value(), "Erro ao cadastrar endereço", e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status).body(error);
		
		
	}
}
