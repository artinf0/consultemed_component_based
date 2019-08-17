package br.com.consultemed.repository.repositories;

import br.com.consultemed.dto.CancelamentosUsuarioDTO;
import br.com.consultemed.models.Agendamento;
import br.com.consultemed.models.Medico;
import br.com.consultemed.utils.DataUtils;
import br.com.consultemed.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgendamentoRepository {

    EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
    EntityManager factory = emf.createEntityManager();



    public List<Agendamento> listarAgendamento() throws Exception {
        this.factory = emf.createEntityManager();
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        try {
            factory.getTransaction().begin();
            Query query = this.factory.createQuery("SELECT object(a) FROM Agendamento as a");
            factory.getTransaction().commit();
            return query.getResultList();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return agendamentos;
    }


    public List<Agendamento> listarAgendamentoPorData(Date data) throws Exception {
        this.factory = emf.createEntityManager();
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        try {

            Query query = this.factory.createQuery("select a from Agendamento a where a.dataAgendamento between :dataInicial and :dataFinal order by a.dataAgendamento asc");
            query.setParameter("dataInicial", DataUtils.dateStartDateMinutes(data));
            query.setParameter("dataFinal", DataUtils.dateEndDateMinutes(data));

            agendamentos = query.getResultList();


        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return agendamentos;
    }

    public List<Agendamento> agendamentoPorMedicoDataEStatus(Medico medico, Date data, Boolean status) throws Exception {
        this.factory = emf.createEntityManager();
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        try {

            Query query = this.factory.createQuery("select a from Agendamento a where a.medico = :medico and a.dataAgendamento = :data and a.status = :status");
            query.setParameter("medico", medico);
            query.setParameter("data", data);
            query.setParameter("status", status);

            agendamentos = query.getResultList();


        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return agendamentos;
    }



    public void salvarAgendamento(Agendamento agendamento) {
        this.factory = emf.createEntityManager();
        try {
            factory.getTransaction().begin();
            if (agendamento.getId() == null) {
                factory.persist(agendamento);
            } else {
                factory.merge(agendamento);
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
        Agendamento agendamento = new Agendamento();

        try {

            agendamento = factory.find(Agendamento.class, id);
            factory.getTransaction().begin();
            factory.remove(agendamento);
            factory.getTransaction().commit();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

    }

    public void cancelarById(Long id) {
        this.factory = emf.createEntityManager();
        Agendamento agendamento = new Agendamento();

        try {

            agendamento = factory.find(Agendamento.class, id);
            agendamento.setStatus(!agendamento.getStatus());

            factory.getTransaction().begin();
            factory.merge(agendamento);
            factory.getTransaction().commit();

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

    }


    public Agendamento buscarById(Long id) {
        this.factory = emf.createEntityManager();
        Agendamento agendamento = new Agendamento();

        try {
            agendamento = factory.find(Agendamento.class, id);
        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return agendamento;
    }


    public List<CancelamentosUsuarioDTO> topUsuariosCancelamentoAnoMes(int ano, int mes) {
        this.factory = emf.createEntityManager();
        List<CancelamentosUsuarioDTO> result = new ArrayList<>();

        try {

            String queryString =
                    "select p.id, p.nome, count(*) as quantidade from tb_agendamento a " +
                    "    inner join tb_paciente p on a.paciente_id = p.id " +
                    " where a.status = false " +
                    " and a.dataCancelamento is not null " +
                    " and year(a.dataCancelamento) = ?1 ";

            if(mes > 0){
                queryString = queryString + " and month(a.dataCancelamento) = ?2 ";
            }

            queryString = queryString + " group by p.id  limit 10 ";

            Query query = this.factory.createNativeQuery(queryString);

            query.setParameter(1, ano);
            if(mes > 0){
                query.setParameter(2, mes);
            }

            List<Object[]> maps = query.getResultList();
            for (Object p : maps ) {
                result.add(
                        CancelamentosUsuarioDTO.builder()
                                .id((((BigInteger) ((Object[]) p)[0])).longValue())
                                .nome((String) ((Object[]) p)[1])
                                .quantidade((((BigInteger) ((Object[]) p)[2])).longValue())
                                .build());
            }

        } catch (Exception e) {
            e.getMessage();
            this.factory.getTransaction().rollback();
        } finally {
            factory.close();
        }

        return result;
    }
}
