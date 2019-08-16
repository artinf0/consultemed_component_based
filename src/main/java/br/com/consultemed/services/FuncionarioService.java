package br.com.consultemed.services;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Funcionario;
import br.com.consultemed.repository.repositories.FuncionarioRepository;
import br.com.consultemed.repository.repositories.PerfilRepository;

import javax.inject.Inject;
import java.util.List;

public class FuncionarioService {

    @Inject
    private FuncionarioRepository repository;
    @Inject
    private PerfilRepository perfilRepository;

    public List<Funcionario> listarFuncionario() throws Exception{
        return this.repository.listarFuncionario();
    }

    public void salvarFuncionario(Funcionario funcionario) throws Exception {
        funcionario.setSenha(Criptografia.criptografar(funcionario.getSenha()));

        if(perfilRepository.getUsuarioByEmail(funcionario.getEmail()).size() > 0){
            throw new EmailCadastradoException("Email: " + funcionario.getEmail() + " ja cadastrado.");
        }

        this.repository.salvarFuncionario(funcionario);
    }

    public void deletarFuncionario(Long id) throws Exception {
        this.repository.deleteById(id);
    }

}
