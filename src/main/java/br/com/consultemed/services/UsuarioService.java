/**
 * 
 */
package br.com.consultemed.services;

import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.repositories.UsuarioRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioService {

	@Inject
	private UsuarioRepository repository;

	public List<Usuario> listarUsuario() throws Exception{
		return this.repository.listarUsuario();
	}

	public void salvarUsuario(Usuario usuario) {
		this.repository.salvarUsuario(usuario);
	}

	public void deletarUsuario(Long id) throws Exception {
		this.repository.deleteById(id);
	}
}
