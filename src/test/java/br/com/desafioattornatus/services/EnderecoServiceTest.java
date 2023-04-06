package br.com.desafioattornatus.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

	private static final Long ID_PESSOA = 1L;
	
	private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(2003, 6, 26);

	private static final String NOME_PESSOA = "Mateus";

	private static final TipoEndereco TIPO_ENDERECO = TipoEndereco.PRINCIPAL;

	private static final String CIDADE = "Curitiba";

	private static final Integer NUMERO = 40;

	private static final String CEP = "34567-890";

	private static final Long ID = 1L;

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

		Endereco response = service.findById(ID);

		assertNotNull(response);
		assertEquals(Endereco.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(LOGRADOURO, response.getLogradouro());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(NUMERO, response.getNumero());
		assertEquals(CEP, response.getCep());
		assertEquals(TIPO_ENDERECO, response.getTipoEndereco());

	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Objeto Não Encontrado! Id: " + anyLong() + ", Tipo: " + Endereco.class.getName()));
			
		try {
			service.findById(ID);
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + 0 + ", Tipo: " + Endereco.class.getName(), e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAListOfEnderecos() {
		when(repository.findAllByPessoa(anyLong())).thenReturn(List.of(endereco));
		
		List<Endereco> response = service.findAll(ID_PESSOA);
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(Endereco.class, response.get(0).getClass());
		assertEquals(ID, response.get(0).getId());
		assertEquals(LOGRADOURO, response.get(0).getLogradouro());
		assertEquals(CIDADE, response.get(0).getCidade());
		assertEquals(NUMERO, response.get(0).getNumero());
		assertEquals(CEP, response.get(0).getCep());
		assertEquals(TIPO_ENDERECO, response.get(0).getTipoEndereco());
				
	}

	@Test
	void whenCreateThenReturnSucess() {
		
		when(pessoaService.findById(ID_PESSOA)).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		
		
		assertNotNull(pessoa);
		
		Endereco response = service.create(pessoa.getId(), endereco2);
		
		assertNotNull(response);
		assertEquals(Endereco.class, response.getClass());
		assertEquals(2, response.getId());
		assertEquals("Rua x", response.getLogradouro());
		assertEquals("São Paulo", response.getCidade());
		assertEquals(453, response.getNumero());
		assertEquals("53432-421", response.getCep());
		assertEquals(TipoEndereco.SECUNDARIO, response.getTipoEndereco());
		
	}
	
	@Test
	void whenCreateThenReturnAEnderecoException() {
		
		when(pessoaService.findById(ID_PESSOA)).thenReturn(pessoa);
		when(repository.save(any())).thenReturn(endereco2);
		endereco2.setTipoEndereco(TipoEndereco.PRINCIPAL);
		
		
		try {
			
			service.create(pessoa.getId(), endereco2);
			
			
		}catch(Exception e) {
			assertEquals(EnderecoException.class, e.getClass());
			assertEquals("Já existe um endereço principal definido", e.getMessage());
			
		}
	
	}
	

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	private void start() {
		
		endereco = new Endereco(ID, LOGRADOURO, CEP, NUMERO, CIDADE, TIPO_ENDERECO);
		endereco2 = new Endereco(2L, "Rua x", "53432-421", 453, "São Paulo", TipoEndereco.SECUNDARIO);
		pessoa = new Pessoa(ID_PESSOA, NOME_PESSOA, DATA_DE_NASCIMENTO, new ArrayList<>(Arrays.asList(endereco)));
		enderecoOptional = Optional.of(new Endereco(ID, LOGRADOURO, CEP, NUMERO, CIDADE, TIPO_ENDERECO));
	}

}
