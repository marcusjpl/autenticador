package br.com.auth.autenticador.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ecad.sga.rest.model.dto.response.pessoa.UsuarioSistemaDTO;
import org.ecad.sga.rest.model.response.consulta.ConsultaDadosMultiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.auth.autenticador.model.InformacaoUsuario;
import br.com.auth.autenticador.model.LoginDTO;
import br.com.auth.autenticador.model.Usuario;

@Service
public class LoginService {

	@Value("${api.key}")
    private String key;
	
    @Value("${api.url}")
    private String api;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UsuarioService usuarioService;

    
    public ResponseEntity<LoginDTO> logar(Usuario usuario) {
        HttpEntity<Usuario> entity = new HttpEntity<Usuario>(usuario,getHeaders());
        try {
        	ResponseEntity<UsuarioSistemaDTO> response = restTemplate.exchange(api+"/auth/login", HttpMethod.POST, entity, UsuarioSistemaDTO.class);
        	UsuarioSistemaDTO dto = response.getBody();
        	String token = usuarioService.logarUsuario(dto);
        	LoginDTO retorno = new LoginDTO(usuario.getLogin(),token,"sucesso");
        	return new ResponseEntity<LoginDTO>(retorno, HttpStatus.OK);
		} catch (Exception e) {
			LoginDTO retorno = new LoginDTO(usuario.getLogin(), null, "Usuário ou senha incorreto");
			return new ResponseEntity<LoginDTO>(retorno, HttpStatus.NOT_FOUND);
		}
    }
    
    public ResponseEntity<InformacaoUsuario> getInformacaoUsuario(String token) {
    	InformacaoUsuario retorno = new InformacaoUsuario();
    	if (usuarioService.isLogado(token)) {
    		String nome = usuarioService.getUsuarioByToken(token);
    		
    		Map<String, Object> login = new HashMap<String, Object>();
			login.put("LOGIN", nome);
			Map<String, Object> request = new HashMap<String, Object>();
			request.put("parametros", login);
    		
    		HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(request,getHeaders(nome));
            try {
            	ResponseEntity<ConsultaDadosMultiResponse> response = restTemplate.exchange(api+"/r/ObterInformacaoLogin", HttpMethod.POST, entity, ConsultaDadosMultiResponse.class);
            	ConsultaDadosMultiResponse consultaDadosResponse = response.getBody();
            	if (consultaDadosResponse.getListas() != null && !consultaDadosResponse.getListas().isEmpty()) {
            		Map<String, Object> funcionario = consultaDadosResponse.getListas().get("FUNCIONARIO").get(0);
            		retorno.setNomLogin(funcionario.get("NOMLOGIN") != null ? funcionario.get("NOMLOGIN").toString() : null);
            		retorno.setEmail(funcionario.get("EMAIL") != null ? funcionario.get("EMAIL").toString() : null);
            		retorno.setUnidade(funcionario.get("UNIDADE") != null ? funcionario.get("UNIDADE").toString() : null);
            		retorno.setNomeCompleto(funcionario.get("NOMCOMPLETO") != null ? funcionario.get("NOMCOMPLETO").toString() : null);
            		retorno.setUrlImg(funcionario.get("URLIMG") != null ? funcionario.get("URLIMG").toString() : null);
            		retorno.setIndTema(funcionario.get("IND_TEMA") != null ? funcionario.get("IND_TEMA").toString() : null);
            		retorno.setMatricula(funcionario.get("NRO_MATRICULA") != null ? funcionario.get("NRO_MATRICULA").toString() : null);
            		retorno.setSiglaUnidade(funcionario.get("SGL_UF_IDENT") != null ? funcionario.get("SGL_UF_IDENT").toString() : null);
            	}
            	
            	return new ResponseEntity<InformacaoUsuario>(retorno, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<InformacaoUsuario>(retorno, HttpStatus.NOT_FOUND);
    		}
    	}
		return new ResponseEntity<InformacaoUsuario>(retorno, HttpStatus.NOT_FOUND);
    }

    public Boolean isLogado(String token) {
    	return usuarioService.isLogado(token);
    }

    public void logout(String token) {
    	usuarioService.logoutUsuario(token);
    }

    public List<String> getUsuariosLogados() {
    	return usuarioService.getUsuariosLogados();
    }

    private HttpHeaders getHeaders() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE.toString());
    	headers.set("Authorization", key);
    	return headers;
    }
    
    private HttpHeaders getHeaders(String user) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE.toString());
    	headers.set("Authorization", key);
    	headers.set("USER", user);
    	return headers;
    }
    
}