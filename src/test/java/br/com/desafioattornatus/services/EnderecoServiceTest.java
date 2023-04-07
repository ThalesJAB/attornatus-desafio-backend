package br.com.desafioattornatus.services;

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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.desafioattornatus.entities.Endereco;
import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.entities.enums.TipoEndereco;
import br.com.desafioattornatus.repositories.EnderecoRepository;
import br.com.desafioattornatus.repositories.PessoaRepository;
import br.com.desafioattornatus.services.exceptions.EnderecoException;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class EnderecoServiceTest {

	private static final Long ID_ENDERECO2 = 2L;

	private static final TipoEndereco TIPO_ENDERECO2 = TipoEndereco.SECUNDARIO;

	private static final String CEP2 = "53432-421";

	private static final Integer NUMERO2 = 453;

	private static final String CIDADE2 = "São Paulo";

	private static final String LOGRADOURO2 = "Rua x";

	private static final Long ID_PESSOA = 1L;

	private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2003, 6, 26);

	private static final String NOME_PESSOA = "Mateus";

	private static final TipoEndereco TIPO_ENDERECO = TipoEndereco.PRINCIPAL;

	private static final String CIDADE = "Curitiba";

	private static final Integer NUMERO = 40;

	private static final String CEP = "34567-890";

	private static final Long ID_ENDERECO = 1L;

	private static final String LOGRADOURO = "Rua D";

	@InjectMocks
	private EnderecoService service;

	@Mock
	private EnderecoRepository repository;

	@Mock
	private PessoaRepository pessoaRepository;

	@Mock
	private PessoaService pessoaService;

	private Endereco endereco;

	private Endereco endereco2;

	private Pessoa pessoa;

	private Optional<Endereco> enderecoOptional;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindByIdThenReturnAnEnderecoInstance() {
		when(repository.findById(anyLong())).thenReturn(enderecoOptional);

		Endereco response = service.findById(ID_ENDERECO);

		assertNotNull(response);
		assertEquals(Endereco.class, response.getClass());
		assertEquals(ID_ENDERECO, response.getId());
		assertEquals(LOGRADOURO, response.getLogradouro());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(NUMERO, response.getNumero());
		assertEquals(CEP, response.getCep());
		assertEquals(TIPO_ENDERECO, response.getTipoEndereco());

	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(ID_ENDERECO)).thenReturn(enderecoOptional);
		
		try {
			service.findById(ID_ENDERECO2);
			
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_ENDERECO2 + ", Tipo: " + Endereco.class.getName(), e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfEnderecos() {
		when(repository.findAllByPessoa(anyLong())).thenReturn(List.of(endereco));
		
		List<Endereco> response = service.findAll(ID_PESSOA);
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(Endereco.class, response.get(0).getClass());
		assertEquals(ID_ENDERECO, response.get(0).getId());
		assertEquals(LOGRADOURO, response.get(0).getLogradouro());
		assertEquals(CIDADE, response.get(0).getCidade());
		assertEquals(NUMERO, response.get(0).getNumero());
		assertEquals(CEP, response.get(0).getCep());
		assertEquals(TIPO_ENDERECO, response.get(0).getTipoEndereco());
				
	}

	@Test
	void whenCreateThenReturnSucess() {
		
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		
		
		assertNotNull(pessoa);
		
		Endereco response = service.create(ID_PESSOA, endereco2);
		
		assertNotNull(response);
		assertEquals(Endereco.class, response.getClass());
		assertEquals(ID_ENDERECO2, response.getId());
		assertEquals(LOGRADOURO2, response.getLogradouro());
		assertEquals(CIDADE2, response.getCidade());
		assertEquals(NUMERO2, response.getNumero());
		assertEquals(CEP2, response.getCep());
		assertEquals(TIPO_ENDERECO2, response.getTipoEndereco());
		
	}

	@Test
	void whenCreateThenReturnAnEnderecoException() {
		
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		endereco2.setTipoEndereco(TipoEndereco.PRINCIPAL);
		
		
		try {
			
			service.create(ID_PESSOA, endereco2);
			
			
		}catch(Exception e) {
			assertEquals(EnderecoException.class, e.getClass());
			assertEquals("Já existe um endereço principal definido", e.getMessage());
			
		}
	
	}

	@Test
	void whenUpdateThenReturnSucess() {
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco);
		
		
		assertNotNull(pessoa);
		
		Endereco response = service.update(ID_PESSOA, ID_ENDERECO, endereco);
		
		assertNotNull(response);
		assertEquals(Endereco.class, response.getClass());
		assertEquals(ID_ENDERECO, response.getId());
		assertEquals(LOGRADOURO, response.getLogradouro());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(NUMERO, response.getNumero());
		assertEquals(CEP, response.getCep());
		
	}

	@Test
	void whenUpdateThenReturnAnEnderecoException() {
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		
		try {
			endereco2.setTipoEndereco(TipoEndereco.PRINCIPAL);
			pessoa.addEndereco(endereco2);
			service.update(ID_PESSOA, ID_ENDERECO2, endereco2);
			
			
		}catch(Exception e) {
			assertEquals(EnderecoException.class, e.getClass());
			assertEquals("Já existe um endereço principal definido", e.getMessage());
			
		}
		
	}

	@Test
	void whenUpdateThenReturnAnObjectNotFoundException() {
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		
		try {
			
			service.update(ID_PESSOA, ID_ENDERECO2, endereco2);
			
			
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_ENDERECO2 + ", Tipo: " + Endereco.class.getName(), e.getMessage());
			
		}
	}

	@Test
	void whenDeleteThenReturnSucess() {
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		doNothing().when(repository).deleteById(anyLong());
		
		service.delete(ID_PESSOA, ID_ENDERECO);
		
		verify(repository, times(1)).deleteById(anyLong());;
		
	}

	@Test
	void whenDeleteThenReturnAnObjectNotFoundException() {
		when(pessoaService.findById(anyLong())).thenReturn(pessoa);
		
		try {
		
			service.delete(ID_PESSOA, ID_ENDERECO2);
		
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_ENDERECO2 + ", Tipo: " + Endereco.class.getName(), e.getMessage());
		}
	}

	private void start() {

		endereco = new Endereco(ID_ENDERECO, LOGRADOURO, CEP, NUMERO, CIDADE, TIPO_ENDERECO);
		endereco2 = new Endereco(ID_ENDERECO2, LOGRADOURO2, CEP2, NUMERO2, CIDADE2, TIPO_ENDERECO2);
		pessoa = new Pessoa(ID_PESSOA, NOME_PESSOA, DATA_DE_NASCIMENTO, new ArrayList<>(Arrays.asList(endereco)));
		enderecoOptional = Optional.of(new Endereco(ID_ENDERECO, LOGRADOURO, CEP, NUMERO, CIDADE, TIPO_ENDERECO));
	}

}
