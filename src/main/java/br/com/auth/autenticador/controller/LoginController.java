package br.com.auth.autenticador.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.auth.autenticador.model.InformacaoUsuario;
import br.com.auth.autenticador.model.LoginDTO;
import br.com.auth.autenticador.model.Usuario;
import br.com.auth.autenticador.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;

@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://fanes.ecad.org.br", "http://localhost:8181"}, 
		     allowedHeaders= {"Content-Type","Authorization","USER"})
@RequestMapping(value = "/", produces = "application/json")
@Api(authorizations={@Authorization(value="Authorization")})
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	@PostMapping("logar")
	public ResponseEntity<LoginDTO> logar(@RequestBody Usuario usuario) {
		return loginService.logar(usuario);
	}
	
	@PutMapping("logout/{token}")
	public ResponseEntity<String> logout(@PathVariable String token) {
		loginService.logout(token);
		return new ResponseEntity<String>("Usuário deslogado com sucesso!", HttpStatus.OK);
	}

	@GetMapping("logado/{token}")
	public ResponseEntity<Object> isLogado(@PathVariable("token") String token) {
		HashMap<String,Boolean> map = new HashMap<>();
		map.put("logado", loginService.isLogado(token) );
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	@GetMapping("usuario/{token}")
	public ResponseEntity<InformacaoUsuario> getDadosUsuario(@PathVariable("token") String token) {
		return loginService.getInformacaoUsuario(token);
	}

	@GetMapping("usuarios")
	public ResponseEntity<List<String>> getUsuariosLogados() {
		return new ResponseEntity<List<String>>(loginService.getUsuariosLogados(), HttpStatus.OK);
	}
	

}
