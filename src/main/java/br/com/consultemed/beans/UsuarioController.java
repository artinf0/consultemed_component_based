/**
 * 
 */
package br.com.consultemed.beans;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.services.UsuarioService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@Named
@RequestScoped
public class UsuarioController{

    @Inject @Getter @Setter
    private Usuario usuario;
    @Inject @Getter @Setter
    private Usuario usuarioEditar;
    @Getter @Setter
    private List<Usuario> usuarios;

    @Inject
    private UsuarioService service;

    public String novoUsuario() {
        this.usuario = new Usuario();
        return "/pages/usuarios/addUsuarios.xhtml?faces-redirect=true";
    }

    public String addUsuario() throws Exception {
        this.service.salvarUsuario(this.usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario " + usuario.getNome()+ ", cadastrado com sucesso", null));
        listaUsuario();
        return "/pages/usuarios/usuarios.xhtml";
    }

    public List<Usuario> listaUsuario() throws Exception{
        this.usuarios = this.service.listarUsuario();
        return this.usuarios;
    }

    public String excluir() throws Exception {
        this.usuario = this.usuarioEditar;
        this.service.deletarUsuario(this.usuario.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario " + usuario.getNome()+ ", excluído com sucesso", null));
        listaUsuario();
        return "/pages/usuarios/usuarios.xhtml";
    }

    public String editar() {
        this.usuario = this.usuarioEditar;
        return "/pages/usuarios/addUsuarios.xhtml";
    }
	
	
}
