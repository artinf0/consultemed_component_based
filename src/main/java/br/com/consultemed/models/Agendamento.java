package br.com.consultemed.models;

import br.com.consultemed.utils.DataUtils;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "TB_AGENDAMENTO")
public class Agendamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medico medico;

    private Boolean status;

    @ManyToOne
    private Paciente paciente;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCancelamento;

    public String getDataAgendamentoFormatado() {
        return DataUtils.formatarData(dataAgendamento,"dd/MM/yyyy HH:mm:ss");
    }


}
