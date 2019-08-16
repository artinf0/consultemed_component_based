package br.com.consultemed.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class PerfilDTO {

    private Long id;
    private String CPF;
    private String email;
    private String nome;
    private String telefone;
    private String perfil;

}
