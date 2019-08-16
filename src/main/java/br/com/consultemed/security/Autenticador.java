package br.com.consultemed.security;

import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.models.Usuario;

import java.util.List;

public interface Autenticador {

	public Usuario autenticador2(String login, String senha);
	public List<PerfilDTO> autenticador(String login, String senha) throws Exception;

}
