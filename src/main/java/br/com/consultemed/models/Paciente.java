package br.com.consultemed.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_PACIENTE")
public class Paciente extends Pessoa{



}
