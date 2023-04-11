package br.com.desafioattornatus.resources.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.desafioattornatus.services.exceptions.EnderecoException;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

class ResourceExceptionHandlerTest {

	private static final String TEST_VALIDATION_ERROR_MESSAGE_2 = "Test validation error message 2";
	private static final String TEST_VALIDATION_ERROR_MESSAGE_1 = "Test validation error message 1";
	private static final String TEST_FIELD2 = "testField2";
	private static final String TEST_FIELD1 = "testField1";
	private static final String FORMATO_DE_DATA_INCORRETO = "Formato de data incorreto. Use o formato 'yyyy-MM-dd";
	private static final String BAD_REQUEST = "Bad Request";
	private static final String ERRO_EM_ENDERECO = "Erro em endereço";
	private static final String ERRO_NA_VALIDACAO_DOS_CAMPOS = "Erro na validação dos campos";
	private static final String OBJECT_NOT_FOUND = "Objeto não encontrado";
	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void whenObjectNotFoundExceptionThenReturnThis() {
		ResponseEntity<StandardError> response = exceptionHandler
				.resourceNotFound(new ObjectNotFoundException(OBJECT_NOT_FOUND), new MockHttpServletRequest());

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(OBJECT_NOT_FOUND, response.getBody().getError());
		assertEquals(404, response.getBody().getStatus());

	}

	@Test
	void whenHandleMethodArgumentNotValidExceptionThenReturnValidationErrors() throws NoSuchMethodException, SecurityException {
	
		List<FieldError> errors = new ArrayList<>();
		FieldError erro1 = new FieldError("testObject", TEST_FIELD1, TEST_VALIDATION_ERROR_MESSAGE_1);
		FieldError erro2 = new FieldError("testObject", TEST_FIELD2, TEST_VALIDATION_ERROR_MESSAGE_2);

		errors.add(erro1);
		errors.add(erro2);
		BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "testObject");
		
		bindingResult.addError(erro1);
		bindingResult.addError(erro2);
		
		Method method = ResourceExceptionHandler.class.getMethod("validationError", MethodArgumentNotValidException.class, HttpServletRequest.class);

		MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(new MethodParameter(method, 0), bindingResult);

		
		ResponseEntity<StandardError> response = exceptionHandler.validationError(methodArgumentNotValidException,
				new MockHttpServletRequest());

		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ValidationError.class, response.getBody().getClass());
		assertEquals("Erro na validação dos campos", response.getBody().getError());
		
		
		ValidationError validationError = (ValidationError) response.getBody();
		
		assertEquals(TEST_FIELD1, validationError.getErros().get(0).getFieldName());
		assertEquals(TEST_VALIDATION_ERROR_MESSAGE_1, validationError.getErros().get(0).getMessage());
		assertEquals(TEST_FIELD2, validationError.getErros().get(1).getFieldName());
		assertEquals(TEST_VALIDATION_ERROR_MESSAGE_2, validationError.getErros().get(1).getMessage());
	
	}

	@Test
	void whenHandleHttpMessageNotReadableExceptionThenReturnThis() {
		ResponseEntity<StandardError> response = exceptionHandler.handleHttpMessageNotReadable(
				new HttpMessageNotReadableException(BAD_REQUEST), new MockHttpServletRequest());

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(BAD_REQUEST, response.getBody().getError());
		assertEquals(400, response.getBody().getStatus());

	}

	@Test
	void whenHandleHttpMessageNotReadableExceptionThenReturnAnDateTimeParseException() {

		DateTimeParseException dateTimeParseException = new DateTimeParseException(ERRO_NA_VALIDACAO_DOS_CAMPOS,
				FORMATO_DE_DATA_INCORRETO, 0);
		HttpMessageNotReadableException httpMessageNotReadableException = new HttpMessageNotReadableException(
				BAD_REQUEST, dateTimeParseException);

		ResponseEntity<StandardError> response = exceptionHandler
				.handleHttpMessageNotReadable(httpMessageNotReadableException, new MockHttpServletRequest());

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(ERRO_NA_VALIDACAO_DOS_CAMPOS, response.getBody().getError());
		assertEquals(FORMATO_DE_DATA_INCORRETO, response.getBody().getMessage());
		assertEquals(400, response.getBody().getStatus());

	}

	@Test
	void whenEnderecoExceptionThenReturnThis() {
		ResponseEntity<StandardError> response = exceptionHandler.enderecoError(new EnderecoException(ERRO_EM_ENDERECO),
				new MockHttpServletRequest());

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(ERRO_EM_ENDERECO, response.getBody().getError());
		assertEquals(409, response.getBody().getStatus());

	}

}
