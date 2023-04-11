package br.com.desafioattornatus.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.services.PessoaService;

@SpringBootTest
class PessoaResourceTest {

	private static final LocalDate DATA_DE_NASCIMENTO2 = LocalDate.of(2000, 8, 03);

	private static final String NOME2 = "Thales";

	private static final long ID_PESSOA2 = 2L;

	private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(1969, 11, 15);

	private static final String NOME = "Ricardo";

	private static final long ID_PESSOA = 1L;

	@InjectMocks
	private PessoaResource resource;

	@Mock
	private PessoaService service;

	private ObjectMapper objectMapper;

	private Pessoa pessoa;

	private Pessoa pessoa2;

	private Optional<Pessoa> pessoaOptional;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindByIdThenReturnSucess() {
		when(service.findById(anyLong())).thenReturn(pessoa);
		
		ResponseEntity<Pessoa> response = resource.findById(ID_PESSOA);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertEquals(ID_PESSOA, response.getBody().getId());
		assertEquals(NOME, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.getBody().getDataNascimento());
		
	}

	@Test
	void whenFindAllThenReturnAnListOfPessoas() {
		
		when(service.findAll()).thenReturn(new ArrayList<>(List.of(pessoa, pessoa2)));

		
		ResponseEntity<List<Pessoa>> response = resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(Pessoa.class, response.getBody().get(0).getClass());
		
		assertEquals(2, response.getBody().size());
		assertEquals(ID_PESSOA2, response.getBody().get(1).getId());
		assertEquals(NOME2, response.getBody().get(1).getNome());
		assertEquals(DATA_DE_NASCIMENTO2, response.getBody().get(1).getDataNascimento());
		
	}

	@Test
	void whenCreateReturnSucess() throws JsonProcessingException {
		when(service.create(any())).thenReturn(pessoa);
		
		ResponseEntity<Pessoa> response = resource.create(pessoa);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertNotNull(response.getHeaders().getLocation());
        URI expectedUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        assertEquals(expectedUri, response.getHeaders().getLocation());

        String expectedJson = "{\"id\":1,\"nome\":\"Ricardo\",\"dataNascimento\":\"1969-11-15\",\"enderecos\":null}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
		
		
		assertEquals(ID_PESSOA, response.getBody().getId());
		assertEquals(NOME, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.getBody().getDataNascimento());

	}

	@Test
	void WhenUpdateReturnSucess() throws JsonProcessingException {
		when(service.update(anyLong(), any())).thenReturn(pessoa);
		
		ResponseEntity<Pessoa> response = resource.update(ID_PESSOA, pessoa2);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Pessoa.class, response.getBody().getClass());
		
		assertEquals(ID_PESSOA, response.getBody().getId());
		assertEquals(NOME, response.getBody().getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.getBody().getDataNascimento());
		
	
		String expectedJson = "{\"id\":1,\"nome\":\"Ricardo\",\"dataNascimento\":\"1969-11-15\",\"enderecos\":null}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
	}

	@Test
	void whenDeleteThenReturnSucess() {
		doNothing().when(service).delete(anyLong());
		
		ResponseEntity<Void> response = resource.delete(ID_PESSOA);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		
		verify(service, times(1)).delete(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
	}

	private void start() {
		pessoa = new Pessoa(ID_PESSOA, NOME, DATA_DE_NASCIMENTO, null);
		pessoa2 = new Pessoa(ID_PESSOA2, NOME2, DATA_DE_NASCIMENTO2, null);
		pessoaOptional = Optional.of(new Pessoa(ID_PESSOA, NOME, DATA_DE_NASCIMENTO, null));
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

	}

}
