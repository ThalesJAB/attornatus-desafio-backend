package br.com.desafioattornatus.entities;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Objects;

import javax.swing.text.MaskFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.desafioattornatus.entities.enums.TipoEndereco;
import br.com.desafioattornatus.entities.interfaces.Cep;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @NotBlank(message = "Campo LOGRADOURO é requerido")
    private String logradouro;

    @NotBlank(message = "Campo CEP é requerido")
    @Cep
    private String cep;

    @NotNull(message = "Campo NÚMERO é requerido")
    @Positive(message = "O campo NÚMERO deve ser um número inteiro positivo")
    private Integer numero;

    @NotBlank(message = "Campo CIDADE é requerido")
    private String cidade;
    
    @NotNull(message = "Campo ENDEREÇO é requerido")
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
		cep = getCepFormatado();
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
	
	@JsonIgnore
    public String getCepFormatado() {
        try {
            MaskFormatter formatter = new MaskFormatter("#####-###");
            formatter.setValueContainsLiteralCharacters(false);
            return formatter.valueToString(this.cep);
        } catch (ParseException e) {
            return this.cep;
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

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", logradouro=" + logradouro + ", cep=" + cep + ", numero=" + numero + ", cidade="
				+ cidade + ", tipoEndereco=" + tipoEndereco + "]";
	}
	
	

}
