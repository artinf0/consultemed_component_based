package br.com.consultemed.repository.repositories;

import br.com.consultemed.dto.PerfilDTO;
import br.com.consultemed.utils.JPAUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PerfilRepository {

    EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
    EntityManager factory = emf.createEntityManager();


    public List<PerfilDTO> getUsuarioByEmail(String email) throws Exception {
        this.factory = emf.createEntityManager();

        List<PerfilDTO> result = new ArrayList<>();

        try {
            Query query = factory.createNativeQuery("select u.id, u.CPF, u.email, u.nome, u.telefone, 'usuario' as perfil from tb_usuarios u where EMAIL = ?1  " +
                    " union all " +
                    " select f.id, f.CPF, f.email, f.nome, f.telefone, 'funcionario' as perfil from tb_funcionario f where EMAIL = ?1  " +
                    " union all " +
                    " select m.id, m.CRM, m.EMAIL, m.nome, m.telefone, 'medico' as perfil from tb_medicos m where EMAIL = ?1 ");
            query.setParameter(1, email);


            List<Object[]> maps = query.getResultList();
            for (Object p : maps ) {
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
