package br.com.desafioattornatus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafioattornatus.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
