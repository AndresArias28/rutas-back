package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.Dificultad;
import com.gym.gym_ver2.domain.model.entity.Enfoque;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RutinaDTO {
    private Integer idRutina;
    private String nombre;
    private String descripcion;
    private String fotoRutina;
    private Enfoque enfoque;
    private Dificultad dificultad;
    private List<EjercicioDTO> ejercicios;


    @Data
    @Builder
    public static class EjercicioDTO {
        private Integer idEjercicio;
        private String nombre;
        private String descripcion;
        private String fotoEjercicio;
        private String musculos;
        private Integer series;
        private Integer repeticion;
        private Integer carga;
        private Integer duracion;
    }

}
