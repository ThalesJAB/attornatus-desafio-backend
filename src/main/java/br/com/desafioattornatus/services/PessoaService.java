package br.com.desafioattornatus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.repositories.PessoaRepository;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));

	}

	public Pessoa create(Pessoa obj) {
		obj.setId(null);

		return repository.save(obj);
	}

	public Pessoa update(Long id, Pessoa obj) {

		Pessoa entity = findById(id);

		entity.setDataNascimento(obj.getDataNascimento());
		entity.setNome(obj.getNome());
		
		return repository.save(entity);

	}

	public void delete(Long id) {
		repository.deleteById(id);

	}

}
