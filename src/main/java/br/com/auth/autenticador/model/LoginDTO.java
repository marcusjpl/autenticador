package br.com.auth.autenticador.model;

public class LoginDTO {

	private String login;
	private String token;
	private String msg;

	public LoginDTO(String login, String token, String msg) {
		this.login = login;
		this.token = token;
		this.msg = msg;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
