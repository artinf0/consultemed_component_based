package br.com.consultemed.repository.repositories;

import br.com.consultemed.models.Paciente;
import br.com.consultemed.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
    EntityManager factory = emf.createEntityManager();



    public List<Paciente> listarPaciente() throws Exception {
        this.factory = emf.createEntityManager();
        List<Paciente> paciente = new ArrayList<Paciente>();
        try {
            factory.getTransaction().begin();
            Query query = this.factory.createQuery("SELECT object(p) FROM Paciente as p");
            factory.getTransaction().commit();
            return query.getResultList();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return paciente;
    }


    public void salvarPaciente(Paciente paciente) {
        this.factory = emf.createEntityManager();
        try {
            factory.getTransaction().begin();
            if (paciente.getId() == null) {
                factory.persist(paciente);
            } else {
                factory.merge(paciente);
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
        Paciente paciente = new Paciente();

        try {

            paciente = factory.find(Paciente.class, id);
            factory.getTransaction().begin();
            factory.remove(paciente);
            factory.getTransaction().commit();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

    }

    public Paciente buscarByEmail(String email) throws Exception {
        this.factory = emf.createEntityManager();

        try {
            Query query = factory.createQuery("select p from Paciente p where p.email = :email", Paciente.class);
            query.setParameter("email", email);
            Paciente paciente = (Paciente) query.getSingleResult();
            return paciente;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            factory.close();
        }

        return null;
    }


}
