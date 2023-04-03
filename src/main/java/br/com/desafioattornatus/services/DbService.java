package br.com.desafioattornatus.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioattornatus.entities.Endereco;
import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.entities.enums.TipoEndereco;
import br.com.desafioattornatus.repositories.PessoaRepository;

@Service
public class DbService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
//	@Autowired
//	private EnderecoRepository enderecoRepository;

	public void initDb() {
		

        
		
        Endereco endereco1 = new Endereco(null, "Rua A", "01234-567", 10, "São Paulo", TipoEndereco.PRINCIPAL);
        Endereco endereco2 = new Endereco(null, "Rua B", "12345-678", 20, "Rio de Janeiro", TipoEndereco.SECUNDARIO);
        Endereco endereco3 = new Endereco(null, "Rua C", "23456-789", 30, "Belo Horizonte", TipoEndereco.SECUNDARIO);
        Endereco endereco4 = new Endereco(null, "Rua D", "34567-890", 40, "Curitiba", TipoEndereco.PRINCIPAL);
        Endereco endereco5 = new Endereco(null, "Rua E", "45678-901", 50, "Porto Alegre", TipoEndereco.SECUNDARIO);
        Endereco endereco6 = new Endereco(null, "Rua F", "56789-012", 60, "Salvador", TipoEndereco.SECUNDARIO);
        Endereco endereco7 = new Endereco(null, "Rua G", "67890-123", 70, "Recife", TipoEndereco.PRINCIPAL);
        Endereco endereco8 = new Endereco(null, "Rua H", "78901-234", 80, "Fortaleza", TipoEndereco.SECUNDARIO);
        Endereco endereco9 = new Endereco(null, "Rua I", "89012-345", 90, "Florianópolis", TipoEndereco.PRINCIPAL);
        Endereco endereco10 = new Endereco(null, "Rua J", "90123-456", 100, "Goiânia", TipoEndereco.SECUNDARIO);
        
//        enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3, endereco4, endereco5
//        		, endereco6, endereco7, endereco8, endereco9, endereco10));
        
        Pessoa pessoa1 = new Pessoa(null, "Maria", LocalDate.of(1985, 5, 15), Arrays.asList(endereco1, endereco2, endereco3));
        Pessoa pessoa2 = new Pessoa(null, "João", LocalDate.of(1990, 1, 1), Arrays.asList(endereco4, endereco5, endereco6));
        Pessoa pessoa3 = new Pessoa(null, "José", LocalDate.of(1980, 1, 30), Arrays.asList(endereco7, endereco8));
        Pessoa pessoa4 = new Pessoa(null, "Mateus", LocalDate.of(2003, 6, 26), Arrays.asList(endereco9, endereco10));
        

        pessoaRepository.saveAll(Arrays.asList(pessoa1, pessoa2, pessoa3, pessoa4));
		
		
		
		
	}

}
