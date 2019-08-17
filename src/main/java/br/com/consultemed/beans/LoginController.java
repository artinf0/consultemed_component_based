/**
 * 
 */
package br.com.consultemed.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.consultemed.dto.LoginDTO;
import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.security.AutenticadorService;
import br.com.consultemed.services.UsuarioService;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
/**
 * @author carlosbarbosagomesfilho
 *
 */

@Named
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject @Getter @Setter
	private Usuario usuario;

	@Inject
	private UsuarioService service;

	@Inject
	private AutenticadorService autenticadorService;

	@Inject @Getter @Setter
	private LoginDTO loginDTO;

	final static Logger logger = Logger.getLogger(LoginController.class);

	public LoginController() {

	}

	public String loginUsuario() throws Exception {
		return this.logar();
	}
	
	public String logoutUsuario() {
		this.logout();
		return "/login?faces-redirect=true";
	}

	public String logar() throws Exception {
		List<PerfilDTO> perfil = autenticadorService.autenticador(this.loginDTO.getLogin(), this.loginDTO.getSenha());

		if(perfil.size() > 0) {
			logger.info("Login realizado");
			logger.info(perfil.get(0).toString());
			this.usuario.setNome(perfil.get(0).getNome());
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("perfil", perfil);
			return "/pages/home?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login ou Senha incorreto.", null));
			return "";
		}

	}

	public void logout() {
		logger.info("Logout realizado");
		logger.info(usuario.toString());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
	}

	public boolean isLogado() {
		boolean isLogeded = false;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext();
		PerfilDTO usuario = (PerfilDTO) session.getAttribute("perfil");

		if(usuario != null) {
			isLogeded = true;
		}
		return isLogeded;
	}





	
//	Fase 1: Restore View (Restauração da visão);
//	Fase 2: Apply Request Values (Aplicar valores da requisição);
//	Fase 3: Process Validation (Processar as validações);
//	Fase 4: Update Model Values (Atualizar valores de modelo);
//	Fase 5: Invoke Application (Invocar aplicação);
//	Fase 6: Render Response (Renderizar a resposta).

	
	
}
