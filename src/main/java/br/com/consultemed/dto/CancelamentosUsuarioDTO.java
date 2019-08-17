package br.com.consultemed.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelamentosUsuarioDTO {
    private Long id;
    private String nome;
    private Long quantidade;
}
