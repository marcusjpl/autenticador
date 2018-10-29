package br.com.auth.autenticador.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.auth.autenticador.service.UsuarioService;

@Component
public class SchedulerTaks {
	
	@Autowired
    private UsuarioService usuarioService;
	
	@Autowired
	private JwtService jwtService;
	
	@Scheduled(fixedRate = 300000)
    public void analisaUsuariosLogados() {
		for (String token : usuarioService.getUsuarioMap().keySet()) {
			if (jwtService.isExpirado(token)) {
				usuarioService.logoutUsuario(token);
			}
		}
    }

}
