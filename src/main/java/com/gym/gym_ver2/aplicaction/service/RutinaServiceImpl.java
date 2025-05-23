package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.RutinaDTO;
import com.gym.gym_ver2.domain.model.entity.Dificultad;
import com.gym.gym_ver2.domain.model.entity.Ejercicio;
import com.gym.gym_ver2.domain.model.entity.Rutina;
import com.gym.gym_ver2.domain.model.entity.RutinaEjercicio;
import com.gym.gym_ver2.infraestructure.repository.EjercicioRepository;
import com.gym.gym_ver2.infraestructure.repository.RutinaEjerciciosRepository;
import com.gym.gym_ver2.infraestructure.repository.RutinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RutinaServiceImpl implements  RutinaService {

    private final RutinaRepository rutinaRepo;
    private final EjercicioRepository ejercicioRepo;
    private final RutinaEjerciciosRepository rutinaEjercicioRepo;


    @Override
    @Transactional
    public RutinaDTO crearRutina(RutinaDTO rutinaDTO) {
        Rutina rutina = Rutina.builder()
                .nombre(rutinaDTO.getNombre())
                .descripcion(rutinaDTO.getDescripcion())
                .fotoRutina(rutinaDTO.getFotoRutina())
                .enfoque(rutinaDTO.getEnfoque())
                .dificultad(rutinaDTO.getDificultad())
                .build();

        Rutina savedRutina = rutinaRepo.save(rutina);

        List<RutinaEjercicio> rutinaEjercicios = rutinaDTO.getEjercicios().stream().map(ejDto -> {
            Ejercicio ejercicio = ejercicioRepo.findById(ejDto.getIdEjercicio())
                    .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejDto.getIdEjercicio()));

            return RutinaEjercicio.builder()
                    .idRutina(savedRutina)
                    .idEjercicio(ejercicio)
                    .series(ejDto.getSeries())
                    .repeticiones(ejDto.getRepeticion())
                    .carga(ejDto.getCarga())
                    .duracion(ejDto.getDuracion())
                    .build();

        }).collect(Collectors.toList());

        rutinaEjercicioRepo.saveAll(rutinaEjercicios); // guardar todos los enlaces

        return RutinaDTO.builder().nombre(savedRutina.getNombre())
                .descripcion(savedRutina.getDescripcion())
                .fotoRutina(savedRutina.getFotoRutina())
                .enfoque(savedRutina.getEnfoque())
                .dificultad(Dificultad.valueOf(savedRutina.getDificultad().name()))
                .build();
    }


}
