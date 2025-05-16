package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.Enfoque;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RutinaDTO {
    private String nombre;
    private String descripcion;
    private String fotoRutina;
    private Enfoque enfoque;

}
