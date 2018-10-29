package br.com.auth.autenticador;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.auth.autenticador.service.UsuarioService;

@Configuration
public class AppConfiguration {

	@Bean
	public RestTemplate publicBean() {
		return new RestTemplate();
	}
	
	@Bean
	public UsuarioService secretBean() {
		return new UsuarioService();
	}

}
