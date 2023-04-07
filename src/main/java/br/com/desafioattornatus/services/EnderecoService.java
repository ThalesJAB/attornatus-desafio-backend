package br.com.desafioattornatus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioattornatus.entities.Endereco;
import br.com.desafioattornatus.entities.Pessoa;
import br.com.desafioattornatus.entities.enums.TipoEndereco;
import br.com.desafioattornatus.repositories.EnderecoRepository;
import br.com.desafioattornatus.services.exceptions.EnderecoException;
import br.com.desafioattornatus.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;

	@Autowired
	private PessoaService pessoaService;

	public Endereco findById(Long id) {
		Optional<Endereco> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! Id: " + id + ", Tipo: " + Endereco.class.getName()));
	}

	public List<Endereco> findAll(Long idPessoa) {
		return repository.findAllByPessoa(idPessoa);
	}

	public Endereco create(Long idPessoa, Endereco obj) {

		Pessoa pessoa = pessoaService.findById(idPessoa);

		verificarEndereco(pessoa.getEnderecos(), obj);

		obj = repository.save(obj);

		pessoa.addEndereco(obj);

		pessoaService.update(idPessoa, pessoa);

		return obj;
	}

	public Endereco update(Long idPessoa, Long idEndereco, Endereco obj) {
		Pessoa pessoa = pessoaService.findById(idPessoa);

		Endereco enderecoAtualizado = pessoa.getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
				.findFirst().orElseThrow(() -> new ObjectNotFoundException(
						"Objeto Não Encontrado! Id: " + idEndereco + ", Tipo: " + Endereco.class.getName()));

		obj.setId(enderecoAtualizado.getId());
		verificarEndereco(pessoa.getEnderecos(), obj);

		enderecoAtualizado.setCep(obj.getCep());
		enderecoAtualizado.setCidade(obj.getCidade());
		enderecoAtualizado.setLogradouro(obj.getLogradouro());
		enderecoAtualizado.setNumero(obj.getNumero());
		enderecoAtualizado.setTipoEndereco(obj.getTipoEndereco());

		return repository.save(enderecoAtualizado);
	}

	public void delete(Long idPessoa, Long idEndereco) {
		Pessoa pessoa = pessoaService.findById(idPessoa);

		boolean remove = pessoa.getEnderecos().removeIf((e -> e.getId().equals(idEndereco)));

		if (!remove) {
			throw new ObjectNotFoundException(
					"Objeto Não Encontrado! Id: " + idEndereco + ", Tipo: " + Endereco.class.getName());
		}
		repository.deleteById(idEndereco);
	}

	private void verificarEndereco(List<Endereco> enderecos, Endereco obj) {

		if (obj.getTipoEndereco() == TipoEndereco.PRINCIPAL) {

			for (Endereco endereco : enderecos) {

				if (endereco.getTipoEndereco() == TipoEndereco.PRINCIPAL) {

					if (obj.getId() == null || !endereco.equals(obj)) {
						throw new EnderecoException("Já existe um endereço principal definido");
					}

				}
			}
		}

	}

}
