package br.com.desafioattornatus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.desafioattornatus.services.DbService;



@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DbService dbService;
	
	
	@Bean
	public void instanciaBaseDeDados() {
		this.dbService.initDb();
	}
}
