/**
 * 
 */
package br.com.consultemed.services;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.exceptions.EmailCadastradoException;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.repositories.PerfilRepository;
import br.com.consultemed.repository.repositories.UsuarioRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioService implements Serializable {

	@Inject
	private UsuarioRepository repository;
	@Inject
	private PerfilRepository perfilRepository;

	public List<Usuario> listarUsuario() throws Exception{
		return this.repository.listarUsuario();
	}

	public void salvarUsuario(Usuario usuario) throws Exception {
		usuario.setSenha(Criptografia.criptografar(usuario.getSenha()));

		if(perfilRepository.getUsuarioByEmail(usuario.getEmail()).size() > 0){
			throw new EmailCadastradoException("Email: " + usuario.getEmail() + " ja cadastrado.");
		}

		this.repository.salvarUsuario(usuario);
	}

	public void deletarUsuario(Long id) throws Exception {
		this.repository.deleteById(id);
	}


}
