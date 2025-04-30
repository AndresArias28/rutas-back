package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.Persona;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UsuarioDTO {
     private Persona idPersona;
     private String nombreUsuario;
     private String emailUsuario;
     private Integer rol;

}

