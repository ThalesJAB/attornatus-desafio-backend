package br.com.desafioattornatus.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafioattornatus.entities.Endereco;
import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.entities.enums.TipoEndereco;
import br.com.desafioattornatus.services.EnderecoService;

@SpringBootTest
class EnderecoResourceTest {
	private static final Long ID_PESSOA = 1L;

	private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2003, 6, 26);

	private static final String NOME_PESSOA = "Mateus";
	
	private static final Long ID_ENDERECO2 = 2L;

	private static final TipoEndereco TIPO_ENDERECO2 = TipoEndereco.SECUNDARIO;

	private static final String CEP2 = "53432-421";

	private static final Integer NUMERO2 = 453;

	private static final String CIDADE2 = "São Paulo";

	private static final String LOGRADOURO2 = "Rua x";

	private static final TipoEndereco TIPO_ENDERECO = TipoEndereco.PRINCIPAL;

	private static final String CIDADE = "Curitiba";

	private static final Integer NUMERO = 40;

	private static final String CEP = "34567-890";

	private static final Long ID_ENDERECO = 1L;

	private static final String LOGRADOURO = "Rua D";
	
	@InjectMocks
	private EnderecoResource resource;
	
	@Mock
	private EnderecoService service;

	private Endereco endereco;

	private Endereco endereco2;

	private Pessoa pessoa;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}


	@Test
	void whenFindAllThenReturnAnListOfPessoa() {
		
		when(service.findAll(anyLong())).thenReturn(new ArrayList<>(List.of(endereco, endereco2)));

		
		ResponseEntity<List<Endereco>> response = resource.findAll(ID_PESSOA);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(Endereco.class, response.getBody().get(0).getClass());
		
		assertEquals(2, response.getBody().size());
		assertEquals(ID_ENDERECO, response.getBody().get(0).getId());
		assertEquals(LOGRADOURO, response.getBody().get(0).getLogradouro());
		assertEquals(CIDADE, response.getBody().get(0).getCidade());
		assertEquals(NUMERO, response.getBody().get(0).getNumero());
		assertEquals(CEP, response.getBody().get(0).getCep());
		assertEquals(TIPO_ENDERECO, response.getBody().get(0).getTipoEndereco());

	}

	@Test
	void whenCreateReturnSucess() throws JsonProcessingException {
		when(service.create(anyLong(), any())).thenReturn(endereco);
		
		ResponseEntity<Endereco> response = resource.create(ID_PESSOA, endereco);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Endereco.class, response.getBody().getClass());
		
		assertEquals(ID_ENDERECO, response.getBody().getId());
		assertEquals(LOGRADOURO, response.getBody().getLogradouro());
		assertEquals(CIDADE, response.getBody().getCidade());
		assertEquals(NUMERO, response.getBody().getNumero());
		assertEquals(CEP, response.getBody().getCep());
		assertEquals(TIPO_ENDERECO, response.getBody().getTipoEndereco());
		
        String expectedJson = "{\"id\":1,\"logradouro\":\"Rua D\",\"cep\":\"34567-890\",\"numero\":40,\"cidade\":\"Curitiba\",\"tipoEndereco\":\"PRINCIPAL\"}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
	
		
	}

	@Test
	void whenUpdateReturnSucess() throws JsonProcessingException {
		when(service.update(anyLong(),  anyLong(), any())).thenReturn(endereco);
		
		ResponseEntity<Endereco> response = resource.update(ID_PESSOA, ID_ENDERECO, endereco2);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(Endereco.class, response.getBody().getClass());
		
		
		assertEquals(ID_ENDERECO, response.getBody().getId());
		assertEquals(LOGRADOURO, response.getBody().getLogradouro());
		assertEquals(CIDADE, response.getBody().getCidade());
		assertEquals(NUMERO, response.getBody().getNumero());
		assertEquals(CEP, response.getBody().getCep());
		assertEquals(TIPO_ENDERECO, response.getBody().getTipoEndereco());
		
        String expectedJson = "{\"id\":1,\"logradouro\":\"Rua D\",\"cep\":\"34567-890\",\"numero\":40,\"cidade\":\"Curitiba\",\"tipoEndereco\":\"PRINCIPAL\"}";
        String atualJson = objectMapper.writeValueAsString(response.getBody());
        assertEquals(expectedJson, atualJson);
		
		
	}

	@Test
	void testDelete() {
		doNothing().when(service).delete(anyLong(), anyLong());
		
		ResponseEntity<Void> response = resource.delete(ID_PESSOA, ID_ENDERECO);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		
		verify(service, times(1)).delete(anyLong(), anyLong());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	

	private void start() {
		endereco = new Endereco(ID_ENDERECO, LOGRADOURO, CEP, NUMERO, CIDADE, TIPO_ENDERECO);
		endereco2 = new Endereco(ID_ENDERECO2, LOGRADOURO2, CEP2, NUMERO2, CIDADE2, TIPO_ENDERECO2);
		pessoa = new Pessoa(ID_PESSOA, NOME_PESSOA, DATA_DE_NASCIMENTO, new ArrayList<>(Arrays.asList(endereco, endereco2)));;
		objectMapper = new ObjectMapper();
	}

}
