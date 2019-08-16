package br.com.consultemed.filters;

import br.com.consultemed.dto.PerfilDTO;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FilterLoggin
 */
@WebFilter(filterName = "/filterLoggin", urlPatterns = {"/pages/*"})
public class FilterLogin implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//RECUPERA A SESSAO
		//INVALIDA A SESSAO
		//VERIFICA SE O USUÁRIO QUE ESTÁ ACESSO O LOGIN, EXISTE E TEM AS CREDENCIAIS DE ACESSO AO SISTEMA
			//SE EXISTE E TEM ACESSO, COLOCA O USUÁRIO NA SESSÃO E DIRECIONA PARA A HOME DO SISTEMA
			//SE NÃO EXISTE, REDIRECIONA PARA A TELA DE LOGIN COM A MENSAGEM DE USUÁRIO INVÁLIDO, SENHA E OU LOGIN INVÁLIDOS

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		PerfilDTO usuarioLogado = null;
		try{
			usuarioLogado = (PerfilDTO) ((ArrayList)httpServletRequest.getSession(true).getAttribute("perfil")).get(0);
		}catch (Exception e){}

		if(usuarioLogado == null){
			RequestDispatcher rd = request.getRequestDispatcher("/login.xhtml");
			rd.forward(request, response);
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {
		
	}
}
