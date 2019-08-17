package br.com.consultemed.beans;

import br.com.consultemed.dto.CancelamentosUsuarioDTO;
import br.com.consultemed.exceptions.HorarioAgendamenteException;
import br.com.consultemed.models.Agendamento;
import br.com.consultemed.models.Medico;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.services.AgendamentoService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Named
@RequestScoped
public class AgendamentoController {

    @Inject @Getter @Setter
    private Agendamento agendamento;
    @Inject @Getter @Setter
    private Medico medico;
    @Inject @Getter @Setter
    private Paciente paciente;
    @Inject @Getter @Setter
    private Agendamento agendamentoEditar;
    @Getter @Setter
    private List<Agendamento> agendamentos;
    @Getter @Setter
    private Date periodoCancelamento;
    @Getter @Setter
    private List<CancelamentosUsuarioDTO> listaCancelamentosUsuarioDTO;

    @Inject
    private AgendamentoService service;



    public String novoAgendamento() {
        this.agendamento = new Agendamento();
        this.medico = new Medico();
        this.paciente = new Paciente();
        return "/pages/agendamentos/addAgendamentos.xhtml?faces-redirect=true";
    }

    public String addAgendamento() throws Exception {
        try{
            agendamento.setMedico(this.medico);
            agendamento.setPaciente(this.paciente);
            this.service.salvarAgendamento(this.agendamento);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Agendamento cadastrado com sucesso", null));
            listaAgendamento();
            return "/pages/agendamentos/agendamentos.xhtml";
        }catch (HorarioAgendamenteException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), null));
            return "";
        }
    }

    public List<Agendamento> listaAgendamento() throws Exception{
        this.agendamentos = this.service.listarAgendamento();
        return this.agendamentos;
    }

    public List<Agendamento> listaAgendamentoPorData(Date dataAgendamento) throws Exception {
        this.agendamentos = this.service.listarAgendamentoPorData(dataAgendamento);
        return this.agendamentos;
    }

    public String excluir() throws Exception {
        this.agendamento = this.agendamentoEditar;
        this.service.deletarAgendamento(this.agendamento.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Agendamento de " +agendamento.getPaciente().getNome()+ ", excluído com sucesso", null));
        listaAgendamento();
        return "/pages/agendamentos/agendamentos.xhtml";
    }

    public String cancelar() throws Exception {
        this.agendamento = this.agendamentoEditar;
        this.service.cancelarAgendamento(this.agendamento.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Agendamento de " +agendamento.getPaciente().getNome()+ ", " + (this.agendamento.getStatus() ? "cancelado" : "ativado") + " com sucesso", null));
        listaAgendamento();
        return "/pages/agendamentos/agendamentos.xhtml";
    }

    public String editar() {
        this.agendamento = this.agendamentoEditar;
        this.medico = this.agendamento.getMedico();
        this.paciente = this.agendamento.getPaciente();
        return "/pages/agendamentos/addAgendamentos.xhtml";
    }

    public List<CancelamentosUsuarioDTO>  topUsuariosCancelamentoAno(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        return this.service.topUsuariosCancelamentoAno(c.get(Calendar.YEAR));
    }

    public List<CancelamentosUsuarioDTO>  topUsuariosCancelamentoAnoMes(){
        if(this.periodoCancelamento == null){
            this.periodoCancelamento = new Date();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(this.periodoCancelamento);

        listaCancelamentosUsuarioDTO = this.service.topUsuariosCancelamentoAnoMes(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1);

        return listaCancelamentosUsuarioDTO;
    }

    public void listarAgendamentoPorPaciente() throws Exception {
        this.agendamentos = this.service.listarAgendamentoPorPaciente(this.paciente);
    }

    public Date getHoraCorrente(){
        return new Date();
    }
}
