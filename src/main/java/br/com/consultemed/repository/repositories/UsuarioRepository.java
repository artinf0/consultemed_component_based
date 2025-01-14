/**
 * 
 */
package br.com.consultemed.repository.repositories;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.models.Usuario;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho
 *
 */
public class UsuarioRepository {

	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();

	public List<Usuario> listarUsuario() throws Exception {
		this.factory = emf.createEntityManager();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			factory.getTransaction().begin();
			Query query = this.factory.createQuery("SELECT object(u) FROM Usuario as u");
			factory.getTransaction().commit();
			return query.getResultList();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

		return usuarios;
	}


	public void salvarUsuario(Usuario usuario) {
		this.factory = emf.createEntityManager();
		try {
			factory.getTransaction().begin();
			if (usuario.getId() == null) {
				factory.persist(usuario);
			} else {
				factory.merge(usuario);
			}
			factory.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();

		} finally {
			factory.close();
		}
	}

	public void deleteById(Long id) throws Exception {
		this.factory = emf.createEntityManager();
		Usuario usuario = new Usuario();

		try {

			usuario = factory.find(Usuario.class, id);
			factory.getTransaction().begin();
			factory.remove(usuario);
			factory.getTransaction().commit();

		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			factory.close();
		}

	}

	public Usuario buscarByEmail(String email) throws Exception {
		this.factory = emf.createEntityManager();

		try {
			Query query = factory.createQuery("select u from Usuario u where u.email = :email", Usuario.class);
			query.setParameter("email", email);
			Usuario usuario = (Usuario) query.getSingleResult();
			return usuario;
		} catch (Exception e) {
			e.getMessage();
		} finally {
			factory.close();
		}

		return null;
	}

}
