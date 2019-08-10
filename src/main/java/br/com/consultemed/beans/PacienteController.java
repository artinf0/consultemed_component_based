package br.com.consultemed.beans;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.PacienteService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class PacienteController {

    @Inject @Getter @Setter
    private Paciente paciente;
    @Inject @Getter @Setter
    private Paciente pacienteEditar;
    @Getter @Setter
    private List<Paciente> pacientes;

    @Inject
    private PacienteService service;

    public PacienteController() {
    }

    public String novoPaciente() {
        this.paciente = new Paciente();
        return "/pages/pacientes/addPacientes.xhtml?faces-redirect=true";
    }

    public String addPaciente() throws Exception {
        this.service.salvarPaciente(this.paciente);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente " +paciente.getNome()+ ", cadastrado com sucesso", null));
        listaPacientes();
        return "/pages/pacientes/pacientes.xhtml";
    }

    public List<Paciente> listaPacientes() throws Exception{
        this.pacientes = this.service.listarPaciente();
        return this.pacientes;
    }

    public String excluir() throws Exception {
        this.paciente = this.pacienteEditar;
        this.service.deletarPaciente(this.paciente.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Paciente " +paciente.getNome()+ ", excluído com sucesso", null));
        listaPacientes();
        return "/pages/pacientes/pacientes.xhtml";
    }

    public String editar() {
        this.paciente = this.pacienteEditar;
        return "/pages/pacientes/addPacientes.xhtml";
    }

}
