package br.com.consultemed.services;

import br.com.consultemed.models.Funcionario;
import br.com.consultemed.repository.repositories.FuncionarioRepository;

import javax.inject.Inject;
import java.util.List;

public class FuncionarioService {

    @Inject
    private FuncionarioRepository repository;

    public List<Funcionario> listarFuncionario() throws Exception{
        return this.repository.listarFuncionario();
    }

    public void salvarFuncionario(Funcionario paciente) {
        this.repository.salvarFuncionario(paciente);
    }

    public void deletarFuncionario(Long id) throws Exception {
        this.repository.deleteById(id);
    }

}
