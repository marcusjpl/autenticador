package br.com.auth.autenticador.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ecad.sga.rest.model.dto.response.pessoa.UsuarioSistemaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.ApplicationScope;

import br.com.auth.autenticador.config.JwtService;

@ApplicationScope
public class UsuarioService {
	
	@Autowired
	private JwtService jwtService;
	
	/** <token,UsuarioDTO> */
	private Map<String,UsuarioSistemaDTO> usuarioMap = new HashMap<String, UsuarioSistemaDTO>();
	
	/** <loginName,token> */
	private Map<String,String> loginMap = new HashMap<String, String>();
	
	public void limparUsuarios() {
		usuarioMap.clear();
		loginMap.clear();
	}
	
	public List<String> getUsuariosLogados() {
		List<String> usuarios = new ArrayList<String>();
		for (String key : usuarioMap.keySet()) {
			usuarios.add(usuarioMap.get(key).getNomeCompleto());
		}
		return usuarios;
	}
	
	public String logarUsuario(UsuarioSistemaDTO usuarioDTO) {
		if (loginMap.get(usuarioDTO.getNomLogin()) != null) {
			usuarioMap.remove(loginMap.get(usuarioDTO.getNomLogin().toUpperCase()));
			loginMap.remove(usuarioDTO.getNomLogin().toUpperCase());
		}
		
		String jwt = jwtService.gerarToken(usuarioDTO.getNomLogin());
		
		loginMap.put(usuarioDTO.getNomLogin().toUpperCase(), jwt);
		usuarioMap.put(jwt, usuarioDTO);
		
		return jwt;
	}

	public void logoutUsuario(String token) {
		if (isLogado(token)) {
			UsuarioSistemaDTO userDTO = usuarioMap.get(token);
			String user = userDTO.getNomLogin().toUpperCase();
			usuarioMap.remove(token);
			if (loginMap.get(user) != null) {
				loginMap.remove(user);
			}
		}
	}
	
	public Boolean isLogado(String token) {
		return isTokenValido(token) && !jwtService.isExpirado(token) && usuarioMap.containsKey(token);
	}
	
	public String getUsuarioByToken(String token) {
		return jwtService.loginWithToken(token);
	}
	
	public Boolean isTokenValido(String token) {
		return jwtService.isTokenValido(token);
	}
	
	public Map<String,UsuarioSistemaDTO> getUsuarioMap() {
		return usuarioMap;
	}

	public void setUsuarioMap(Map<String,UsuarioSistemaDTO> usuarioMap) {
		this.usuarioMap = usuarioMap;
	}

}
