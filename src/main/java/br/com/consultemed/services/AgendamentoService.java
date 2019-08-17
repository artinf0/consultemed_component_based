package br.com.consultemed.services;

import br.com.consultemed.exceptions.HorarioAgendamenteException;
import br.com.consultemed.models.Agendamento;
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
        this.repository.cancelarById(id);
    }
}
