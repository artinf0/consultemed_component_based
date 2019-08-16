/**
 * 
 */
package br.com.consultemed.security;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.consultemed.cripto.Criptografia;
import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.repository.security.AutenticadorRepository;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class AutenticadorService implements Autenticador, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AutenticadorRepository autenticadorRepository;
	
	public Usuario autenticador2(String login, String senha) {
		return this.autenticadorRepository.autenticadorUsuario(login, senha);
	}

	public List<PerfilDTO> autenticador(String login, String senha) throws Exception {
		senha = Criptografia.criptografar(senha);
		return autenticadorRepository.autenticador(login, senha);
	}
}
