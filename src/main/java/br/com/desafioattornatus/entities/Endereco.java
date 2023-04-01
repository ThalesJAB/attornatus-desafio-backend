package br.com.desafioattornatus.entities;

import java.io.Serializable;
import java.util.Objects;

import br.com.desafioattornatus.entities.enums.TipoEndereco;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String logradouro;
	private String cep;
	private Integer numero;
	private String cidade;
	private Integer tipoEndereco;

	public Endereco() {

	}

	public Endereco(Long id, String logradouro, String cep, Integer numero, String cidade, TipoEndereco tipoEndereco) {

		this.id = id;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.cidade = cidade;
		setTipoEndereco(tipoEndereco);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public TipoEndereco getTipoEndereco() {
		return TipoEndereco.valueOf(this.tipoEndereco);
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		if (tipoEndereco != null) {
			this.tipoEndereco = tipoEndereco.getCodigo();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(id, other.id);
	}

}
