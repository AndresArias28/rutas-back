package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.Dificultad;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RutinaEjercicioDTO {

    private Dificultad dificultad; // puede ser ENUM en backend también
    private Integer series;
    private Integer repeticion;
    private Integer carga;
    private Integer duracion;
    private Integer idEjercicio;

}
