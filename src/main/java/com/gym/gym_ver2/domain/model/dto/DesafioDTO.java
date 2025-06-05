package com.gym.gym_ver2.domain.model.dto;

import com.gym.gym_ver2.domain.model.entity.DesafioRealizado;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DesafioDTO {
    private String nombre;
    private String descripcion;
    private String fotoDesafio;
    private String estado;
    private String fechaFin;
    private List<DesafioRealizado> desafiosRealizados;

}
