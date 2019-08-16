package br.com.consultemed.services;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Paciente;
import br.com.consultemed.repository.repositories.PacienteRepository;
import br.com.consultemed.repository.repositories.PerfilRepository;

import javax.inject.Inject;
import java.util.List;

public class PacienteService {

    @Inject
    private PacienteRepository repository;
    @Inject
    private PerfilRepository perfilRepository;

    public List<Paciente> listarPaciente() throws Exception{
        return this.repository.listarPaciente();
    }

    public void salvarPaciente(Paciente paciente) throws Exception {

        if(perfilRepository.getUsuarioByEmail(paciente.getEmail()).size() > 0){
            throw new EmailCadastradoException("Email: " + paciente.getEmail() + " ja cadastrado.");
        }

        this.repository.salvarPaciente(paciente);
    }

    public void deletarPaciente(Long id) throws Exception {
        this.repository.deleteById(id);
    }
}
