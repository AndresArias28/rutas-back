package com.gym.gym_ver2.domain.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsuarioDTO {
     private String nombreCompleto;
     private String nombreUsuario;
     private String emailUsuario;
     private String identificacion;
     private Integer rol;

}

