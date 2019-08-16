/**
 * 
 */
package br.com.consultemed.models;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.consultemed.security.AutenticadorService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@Named
@SessionScoped
public class UsuarioLogado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter @Setter @Inject
	private Usuario usuario;
	
	@Inject
	private AutenticadorService autenticador;

	public void logar() {
		
		Usuario usuario = this.autenticador.autenticador2(this.usuario.getEmail(), this.usuario.getSenha());
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
	}

	public void logout() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
		
	}
	
	
	public boolean isLogado() {
		boolean isLogeded = false;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		if(usuario != null) {
			isLogeded = true;
		}
		return isLogeded;
	}
	

}
