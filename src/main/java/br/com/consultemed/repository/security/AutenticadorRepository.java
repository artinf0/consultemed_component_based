package br.com.consultemed.repository.security;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.utils.JPAUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AutenticadorRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();

	public Usuario autenticadorUsuario(String login, String senha) {

		Usuario usuario = null;
//		this.factory = emf.createEntityManager();
//
//		try {
//			Query query = this.factory.createNamedQuery("Usuario.loginUsuario");
//			query.setParameter("login", login);
//			query.setParameter("senha", senha);
//			usuario = (Usuario) query.getSingleResult();
//
//			return usuario;
//
//		} catch (Exception e) {
//			e.getMessage();
//			this.factory.getTransaction().rollback();
//
//		}finally {
//			this.factory.close();
//		}

		return usuario;
	}


	public List<PerfilDTO> autenticador(String email, String senha) throws Exception {
		this.factory = emf.createEntityManager();

		List<PerfilDTO> result = new ArrayList<>();

		try {
			Query query = factory.createNativeQuery("select u.id, u.CPF, u.email, u.nome, u.telefone, 'usuario' as perfil from tb_usuarios u where EMAIL = ?1 AND SENHA = ?2 " +
					" union all " +
					" select f.id, f.CPF, f.email, f.nome, f.telefone, 'funcionario' as perfil from tb_funcionario f where EMAIL = ?1 AND SENHA = ?2 " +
					" union all " +
					" select m.id, m.CRM, m.EMAIL, m.nome, m.telefone, 'medico' as perfil from tb_medicos m where EMAIL = ?1 AND SENHA = ?2 ");
			query.setParameter(1, email);
			query.setParameter(2, senha);


			List<Object[]> maps = query.getResultList();
			for (Object p : maps) {
				result.add(
						PerfilDTO.builder()
								.id((((BigInteger) ((Object[]) p)[0])).longValue())
								.CPF((String) ((Object[]) p)[1])
								.email((String) ((Object[]) p)[2])
								.nome((String) ((Object[]) p)[3])
								.telefone((String) ((Object[]) p)[4])
								.perfil((String) ((Object[]) p)[5])
								.build());
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			factory.close();
		}

		return result;
	}
}
