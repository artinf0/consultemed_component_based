package br.com.consultemed.beans;

import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Funcionario;
import br.com.consultemed.services.FuncionarioService;
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
public class FuncionarioController {

    @Inject @Getter @Setter
    private Funcionario funcionario;
    @Inject @Getter @Setter
    private Funcionario funcionarioEditar;
    @Getter @Setter
    private List<Funcionario> funcionarios;

    @Inject
    private FuncionarioService service;

    public String novoFuncionario() {
        this.funcionario = new Funcionario();
        return "/pages/funcionarios/addFuncionarios.xhtml?faces-redirect=true";
    }

    public String addFuncionario() throws Exception {
        try{
            this.service.salvarFuncionario(this.funcionario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Funcionario " +funcionario.getNome()+ ", cadastrado com sucesso", null));
            listaFuncionario();
            return "/pages/funcionarios/funcionarios.xhtml";
        }catch (EmailCadastradoException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), null));
            return "";
        }
    }

    public List<Funcionario> listaFuncionario() throws Exception{
        this.funcionarios = this.service.listarFuncionario();
        return this.funcionarios;
    }

    public String excluir() throws Exception {
        this.funcionario = this.funcionarioEditar;
        this.service.deletarFuncionario(this.funcionario.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Funcionario " +funcionario.getNome()+ ", excluído com sucesso", null));
        listaFuncionario();
        return "/pages/funcionarios/funcionarios.xhtml";
    }

    public String editar() {
        this.funcionario = this.funcionarioEditar;
        return "/pages/funcionarios/addFuncionarios.xhtml";
    }

}
