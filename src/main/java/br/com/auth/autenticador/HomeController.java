package br.com.auth.autenticador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping	("/swagger")
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}
	
	@GetMapping	("/")
	public String home() {
		return "home.html";
	}
	
	@GetMapping	("")
	public String homeEmpty() {
		return "home.html";
	}
	
}