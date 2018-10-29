package br.com.auth.autenticador.model;

public class InformacaoUsuario {
	
	private String nomeCompleto;
	private String email;
	private String matricula;
	private String unidade;
	private String siglaUnidade;
	private String urlImg;
	private String indTema;
	private String nomLogin;
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getSiglaUnidade() {
		return siglaUnidade;
	}
	public void setSiglaUnidade(String siglaUnidade) {
		this.siglaUnidade = siglaUnidade;
	}
	public String getUrlImg() {
		return urlImg;
	}
	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}
	public String getIndTema() {
		return indTema;
	}
	public void setIndTema(String indTema) {
		this.indTema = indTema;
	}
	public String getNomLogin() {
		return nomLogin;
	}
	public void setNomLogin(String nomLogin) {
		this.nomLogin = nomLogin;
	}
	
}
