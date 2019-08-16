/**
 * 
 */
package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Medico;
import br.com.consultemed.repository.repositories.MedicoRepository;
import br.com.consultemed.repository.repositories.PerfilRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class MedicoService {

	@Inject
	private MedicoRepository repository;
	@Inject
	private PerfilRepository perfilRepository;
	
	public List<Medico> listaMedico() throws Exception{
		return this.repository.listarMedicos();
	}
	
	public void salvarMedico(Medico medico) throws Exception {
		medico.setSenha(Criptografia.criptografar(medico.getSenha()));

		if(perfilRepository.getUsuarioByEmail(medico.getEmail()).size() > 0){
			throw new EmailCadastradoException("Email: " + medico.getEmail() + " ja cadastrado.");
		}

		this.repository.salvarMedico(medico);
	}
	
	public void deletarMedico(Long id) throws Exception {
		this.repository.deleteById(id);
	}
}
