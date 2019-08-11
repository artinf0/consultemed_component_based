package br.com.consultemed.services;

import br.com.consultemed.models.Agendamento;
import br.com.consultemed.repository.repositories.AgendamentoRepository;

import javax.inject.Inject;
import java.util.List;

public class AgendamentoService {

    @Inject
    private AgendamentoRepository repository;

    public List<Agendamento> listarAgendamento() throws Exception{
        return this.repository.listarAgendamento();
    }

    public void salvarAgendamento(Agendamento agendamento) {
        agendamento.setStatus(true);
        this.repository.salvarAgendamento(agendamento);
    }

    public void deletarAgendamento(Long id) throws Exception {
        this.repository.deleteById(id);
    }

    public void cancelarAgendamento(Long id) {
        this.repository.cancelarById(id);
    }
}
