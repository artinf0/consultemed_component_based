package br.com.consultemed.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_PACIENTE")
public class Paciente extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;


}
