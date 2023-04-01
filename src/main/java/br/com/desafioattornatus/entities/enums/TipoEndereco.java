package br.com.desafioattornatus.entities.enums;

public enum TipoEndereco {
	
	
	PRINCIPAL(1),
	SECUNDARIO(2);
	
	
	private int codigo;
	
	private TipoEndereco(int codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public static TipoEndereco valueOf(int codigo) {
		for(TipoEndereco value : TipoEndereco.values()) {
			if(value.getCodigo() == codigo) {
				return value;
			}
		}
		
		throw new IllegalArgumentException("Código TipoEndereco Inválido!!");
	}
	
	

}
