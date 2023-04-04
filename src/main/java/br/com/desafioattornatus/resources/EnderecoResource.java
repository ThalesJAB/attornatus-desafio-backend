package br.com.desafioattornatus.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioattornatus.entities.Endereco;
import br.com.desafioattornatus.services.EnderecoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pessoas/{idPessoa}/enderecos")
public class EnderecoResource {

	@Autowired
	private EnderecoService service;

	@GetMapping
	public ResponseEntity<List<Endereco>> findAll(@PathVariable Long idPessoa) {

		List<Endereco> enderecoList = service.findAll(idPessoa);

		return ResponseEntity.ok().body(enderecoList);

	}

	@PostMapping
	public ResponseEntity<Endereco> create(@Valid @PathVariable Long idPessoa, @RequestBody Endereco endereco) {

		Endereco enderecoNovo = service.create(idPessoa, endereco);

		return ResponseEntity.ok().body(enderecoNovo);

	}

	@PutMapping(value = "/{idEndereco}")
	public ResponseEntity<Endereco> update(@Valid @PathVariable Long idPessoa, @PathVariable Long idEndereco,
			@RequestBody Endereco obj) {
		obj = service.update(idPessoa, idEndereco, obj);

		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{idEndereco}")
	public ResponseEntity<Void> delete(@PathVariable Long idPessoa, @PathVariable Long idEndereco) {
		service.delete(idPessoa, idEndereco);

		return ResponseEntity.noContent().build();
	}

}
