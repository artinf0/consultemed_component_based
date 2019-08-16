package br.com.consultemed.models;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_FUNCIONARIO")
public class Funcionario extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senha;

}
