package br.com.consultemed.repository.repositories;

import br.com.consultemed.models.Funcionario;
import br.com.consultemed.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository {

    EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
    EntityManager factory = emf.createEntityManager();



    public List<Funcionario> listarFuncionario() throws Exception {
        this.factory = emf.createEntityManager();
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        try {
            factory.getTransaction().begin();
            Query query = this.factory.createQuery("SELECT object(f) FROM Funcionario as f");
            factory.getTransaction().commit();
            return query.getResultList();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return funcionarios;
    }


    public void salvarFuncionario(Funcionario funcionario) {
        this.factory = emf.createEntityManager();
        try {
            factory.getTransaction().begin();
            if (funcionario.getId() == null) {
                factory.persist(funcionario);
            } else {
                factory.merge(funcionario);
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
        Funcionario funcionario = new Funcionario();

        try {

            funcionario = factory.find(Funcionario.class, id);
            factory.getTransaction().begin();
            factory.remove(funcionario);
            factory.getTransaction().commit();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

    }

}
