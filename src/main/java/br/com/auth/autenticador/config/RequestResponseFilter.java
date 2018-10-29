//package br.com.auth.autenticador.config;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//@Component
//public class RequestResponseFilter extends GenericFilterBean {
//
//	@Value("${api.key}")
//	private String key;
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		if (telaSwagger(req)) {
//			filterChain.doFilter(request, response);
//			return;
//		} else {
//			if (req.getHeader("Authorization") == null || !key.equals(req.getHeader("Authorization"))) {
//				HttpServletResponse httpResponse = (HttpServletResponse) response;
//				httpResponse.setContentType("application/json");
//				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Hearders obrigatórios não foram definidos");
//				return;
//			}
//			filterChain.doFilter(request, response);
//		}
//	}
//
//	private boolean telaSwagger(HttpServletRequest req) {
//		return req.getRequestURI() != null && (req.getRequestURI().contains("swagger")
//				|| req.getRequestURI().contains("webjars") || req.getRequestURI().contains("/v2/api-docs"));
//	}
//}
