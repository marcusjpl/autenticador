package br.com.auth.autenticador;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.auth.autenticador.config.JwtService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutenticadorApplicationTests {
	
	@Autowired
	private JwtService jwtService;

	@Test
	public void contextLoads() {
		String tokenGerado = jwtService.gerarToken("MLOUREIRO");
		
		System.out.println(tokenGerado);
		
		jwtService.parseJWT(tokenGerado);
		
		jwtService.loginWithToken(tokenGerado);
		
		System.out.println("Data Expiracao:" + jwtService.getDataExpiracao(tokenGerado));
		
		System.out.println("Is Expirado:" + jwtService.isExpirado(tokenGerado));
	}

}
