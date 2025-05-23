package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.Dificultad;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RutinaEjercicioDTO {

    private Dificultad dificultad; // puede ser ENUM en backend también
    private int series;
    private int repeticion;
    private int carga;
    private int duracion;
    private int idEjercicio;

}
