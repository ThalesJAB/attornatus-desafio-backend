package br.com.desafioattornatus.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import br.com.desafioattornatus.repositories.PessoaRepository;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class PessoaServiceTest {

	private static final LocalDate DATA_DE_NASCIMENTO2 = LocalDate.of(2000, 8, 03);

	private static final String NOME2 = "Thales";

	private static final long ID_PESSOA2 = 2L;

	private static final LocalDate DATA_DE_NASCIMENTO = LocalDate.of(1969, 11, 15);

	private static final String NOME = "Ricardo";

	private static final long ID_PESSOA = 1L;

	@InjectMocks
	private PessoaService service;

	@Mock
	private PessoaRepository repository;

	private Pessoa pessoa;

	private Pessoa pessoa2;

	private Optional<Pessoa> pessoaOptional;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		start();
	}

	@Test
	void whenFindByIdThenReturnAnPessoaInstance() {
		when(repository.findById(anyLong())).thenReturn(pessoaOptional);

		Pessoa response = service.findById(ID_PESSOA);

		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(ID_PESSOA, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.getDataNascimento());


	}

	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(ID_PESSOA)).thenReturn(pessoaOptional);
		
		try {
			service.findById(ID_PESSOA2);
			
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA2 + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfPessoas() {
		when(repository.findAll()).thenReturn(List.of(pessoa, pessoa2));
		
		List<Pessoa> response = service.findAll();
		
		assertNotNull(response);
		assertEquals(2, response.size());
		assertEquals(Pessoa.class, response.get(0).getClass());
		assertEquals(ID_PESSOA, response.get(0).getId());
		assertEquals(NOME, response.get(0).getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.get(0).getDataNascimento());

	}

	@Test
	void whenCreateReturnSucess() {
		when(repository.save(any())).thenReturn(pessoa);
		
		Pessoa response = service.create(pessoa);
		
		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(ID_PESSOA, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO, response.getDataNascimento());
		
	}

	@Test
	void whenUpdateReturnSucess() {
		when(repository.findById(any())).thenReturn(pessoaOptional);
		when(repository.save(any())).thenReturn(pessoa2);
		
		Pessoa response = service.update(ID_PESSOA, pessoa);
		
		assertNotNull(response);
		assertEquals(Pessoa.class, response.getClass());
		assertEquals(NOME2, response.getNome());
		assertEquals(DATA_DE_NASCIMENTO2, response.getDataNascimento());
		
	}

	@Test
	void whenUpdateReturnAnObjectNotFoundException() {
		when(repository.findById(any())).thenThrow(new ObjectNotFoundException("Objeto Não Encontrado! Id: " + ID_PESSOA + ", Tipo: " + Pessoa.class.getName()));
		
		try {
			
			service.update(ID_PESSOA, pessoa);
			
		} catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
	
	}

	@Test
	void WhenDeleteReturnSucess() {
		when(repository.findById(any())).thenReturn(pessoaOptional);
		doNothing().when(repository).deleteById(anyLong());
		
		
		service.delete(ID_PESSOA);
		verify(repository, times(1)).deleteById(anyLong());;
	}

	@Test
	void WhenDeleteReturnAnObjectNotFound() {
		when(repository.findById(any())).thenThrow(new ObjectNotFoundException("Objeto Não Encontrado! Id: " + ID_PESSOA + ", Tipo: " + Pessoa.class.getName()));
		
		try {
			service.delete(ID_PESSOA);
		}catch(Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals("Objeto Não Encontrado! Id: " + ID_PESSOA + ", Tipo: " + Pessoa.class.getName(), e.getMessage());
		}
		
		
		
	}

	private void start() {
		pessoa = new Pessoa(ID_PESSOA, NOME, DATA_DE_NASCIMENTO, null);
		pessoa2 = new Pessoa(ID_PESSOA2, NOME2, DATA_DE_NASCIMENTO2, null);
		pessoaOptional = Optional.of(new Pessoa(ID_PESSOA, NOME, DATA_DE_NASCIMENTO, null));
	}
}
