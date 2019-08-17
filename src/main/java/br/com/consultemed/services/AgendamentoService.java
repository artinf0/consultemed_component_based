package br.com.consultemed.services;

import br.com.consultemed.dto.CancelamentosUsuarioDTO;
import br.com.consultemed.exceptions.HorarioAgendamenteException;
import br.com.consultemed.models.Agendamento;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.repositories.AgendamentoRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

public class AgendamentoService {

    @Inject
    private AgendamentoRepository repository;

    public List<Agendamento> listarAgendamento() throws Exception{
        return this.repository.listarAgendamento();
    }

    public List<Agendamento> listarAgendamentoPorData(Date data) throws Exception{
        return this.repository.listarAgendamentoPorData(data);
    }

    public void salvarAgendamento(Agendamento agendamento) throws Exception {
        Boolean statusAgendamento = true;
        if(repository.agendamentoPorMedicoDataEStatus(agendamento.getMedico(), agendamento.getDataAgendamento(), statusAgendamento).size() > 0){
            throw new HorarioAgendamenteException("Horário indisponível para agendamento.");
        }

        agendamento.setStatus(statusAgendamento);
        this.repository.salvarAgendamento(agendamento);
    }

    public void deletarAgendamento(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    public void cancelarAgendamento(Long id) {
        Agendamento agendamento = this.repository.buscarById(id);

        if(agendamento == null){
            throw new RuntimeException("Cancelamento não encontrado");
        }

        if(agendamento.getStatus()){
            agendamento.setDataCancelamento(new Date());
            agendamento.setStatus(false);
        } else {
            agendamento.setDataCancelamento(null);
            agendamento.setStatus(true);
        }

        this.repository.salvarAgendamento(agendamento);
    }

    public List<CancelamentosUsuarioDTO> topUsuariosCancelamentoAno(int ano){
        return this.repository.topUsuariosCancelamentoAnoMes(ano, 0);
    }

    public List<CancelamentosUsuarioDTO> topUsuariosCancelamentoAnoMes(int ano, int mes){
        return this.repository.topUsuariosCancelamentoAnoMes(ano, mes);
    }

    public List<Agendamento> listarAgendamentoPorPaciente(Paciente paciente) throws Exception {
        return this.repository.listarAgendamentoPorPaciente(paciente);
    }
}
