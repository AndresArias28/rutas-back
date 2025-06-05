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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class RutinaServiceImpl implements  RutinaService {

    private static final Logger logger = LoggerFactory.getLogger(RutinaServiceImpl.class);
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
                    .rutina(savedRutina)
                    .ejercicio(ejercicio)
                    .series(ejDto.getSeries())
                    .repeticiones(ejDto.getRepeticiones())
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

    @Override
    public List<RutinaDTO> obtenerRutinas() {
        // Obtener todas las rutinas desde el repositorio
        List<Rutina> rutinas = rutinaRepo.findAll();

        return rutinas.stream().map(rutina -> {
            // Obtener los ejercicios relacionados a la rutina
            List<RutinaEjercicio> ejercicios = rutinaEjercicioRepo.findByRutina(rutina);
            logger.info("Rutina: {} -> ejercicios: {}", rutina.getNombre(), ejercicios.size());
            // Convertir RutinaEjercicio a EjercicioDTO
            List<RutinaDTO.EjercicioDTO> ejercicioDTOs = ejercicios.stream()
                    .map( re -> {
                        Ejercicio ejercicio = re.getEjercicio();
                        return RutinaDTO.EjercicioDTO.builder()
                                .idEjercicio(ejercicio.getIdEjercicio())
                                .nombre(ejercicio.getNombreEjercicio())
                                .descripcion(ejercicio.getDescripcionEjercicio())
                                .musculos(ejercicio.getMusculos())
                                .fotoEjercicio(ejercicio.getFotoEjercicio())
                                .repeticiones(re.getRepeticiones())
                                .series(re.getSeries())
                                .duracion(re.getDuracion())
                                .carga(re.getCarga())
                                .build();
                    })
                    .collect(Collectors.toList());

            // Retornar DTO de rutina
            logger.info("Cantidad de rutinas: {}", rutinas.size());

            return RutinaDTO.builder()
                    .nombre(rutina.getNombre())
                    .descripcion(rutina.getDescripcion())
                    .fotoRutina(rutina.getFotoRutina())
                    .enfoque(rutina.getEnfoque())
                    .dificultad(rutina.getDificultad())
                    .ejercicios(ejercicioDTOs)
                    .build();
        }).collect(Collectors.toList());
    }

}
