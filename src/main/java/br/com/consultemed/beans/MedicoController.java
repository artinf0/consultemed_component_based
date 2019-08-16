/**
 * 
 */
package br.com.consultemed.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Medico;
import br.com.consultemed.services.MedicoService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carlosbarbosagomesfilho
 *
 */
@Named
@RequestScoped
public class MedicoController{
	
	@Getter
	@Setter
	private List<Medico> medicos;

	@Inject
	@Getter
	@Setter
	private Medico medico;
	
	@Getter
	@Setter
	private Medico medicoEditar;
	
	@Inject
	private MedicoService service;
	
	public String editar() {
		this.medico = this.medicoEditar;
		return "/pages/medicos/addMedicos.xhtml";
	}
	
	public String excluir() throws Exception {
			this.medico = this.medicoEditar;
			this.service.deletarMedico(this.medico.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Médico " +medico.getNome()+ ", excluído com sucesso", null));
			listaMedicos();
			return "/pages/medicos/medicos.xhtml";
	}
	
	public String novoMedico() {
		this.medico = new Medico();
		return "/pages/medicos/addMedicos.xhtml?faces-redirect=true";
	}
	
	public String addMedico() throws Exception {
		try{
			this.service.salvarMedico(this.medico);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Médico " +medico.getNome()+ ", cadastrado com sucesso", null));
			listaMedicos();
			return "/pages/medicos/medicos.xhtml";
		}catch (EmailCadastradoException e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
			return "";
		}
	}
	
	public List<Medico> listaMedicos() throws Exception{
		this.medicos = this.service.listaMedico();
		return this.medicos;
	}
	
}
