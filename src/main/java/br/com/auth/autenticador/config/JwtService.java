package br.com.auth.autenticador.config;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
	
	@Value("${auth.secret}")
    private String secret;
	
	@Value("${token.timeout}")
    private String timeout;
	
	public String gerarToken(String nomeLogin) {
		String jwt = Jwts.builder()
			    .setSubject(nomeLogin)
			    .setIssuedAt(new Date())
			    .signWith(SignatureAlgorithm.HS256, secret)
			    .setExpiration(DateUtils.addMinutes(new Date(), new Integer(timeout)))
			    .compact();
		return jwt;
	}
	
	public void parseJWT(String jwt) {
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Expiration: " + claims.getExpiration());
	}
	
	// Se a validade do token tiver expirado será lançado uma 'exception' e o metodo ira retornar um '401'
	public String loginWithToken(String token){
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e){
			System.out.println(e.getMessage());
			System.out.println(e);
			return "Erro";
		}
	}
	
	public Date getDataExpiracao(String jwt) {
		Claims claims = Jwts.parser()         
			       .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
			       .parseClaimsJws(jwt).getBody();
		return claims.getExpiration();
	}
	
	public Boolean isExpirado(String jwt) {
		try {
			return new Date().after(getDataExpiracao(jwt));
		} catch (ExpiredJwtException e) {
			return true;
		}
	}
	
	public Boolean isTokenValido(String jwt) {
		return Jwts.parser().isSigned(jwt);
	}

}
