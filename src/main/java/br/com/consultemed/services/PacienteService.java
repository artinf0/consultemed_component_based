package br.com.consultemed.services;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.PacienteRepository;

import javax.inject.Inject;
import java.util.List;

public class PacienteService {

    @Inject
    private PacienteRepository repository;

    public List<Paciente> listarPaciente() throws Exception{
        return this.repository.listarPaciente();
    }

    public void salvarPaciente(Paciente paciente) {
        this.repository.salvarPaciente(paciente);
    }

    public void deletarPaciente(Long id) throws Exception {
        this.repository.deleteById(id);
    }
}
