package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.RutinaDTO;
import com.gym.gym_ver2.domain.model.entity.Dificultad;
import com.gym.gym_ver2.domain.model.entity.Ejercicio;
import com.gym.gym_ver2.domain.model.entity.Rutina;
import com.gym.gym_ver2.domain.model.entity.RutinaEjercicio;
import com.gym.gym_ver2.infraestructure.repository.EjercicioRepository;
import com.gym.gym_ver2.infraestructure.repository.RutinaEjerciciosRepository;
import com.gym.gym_ver2.infraestructure.repository.RutinaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

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
                    .repeticiones(ejDto.getRepeticion())
                    .carga(ejDto.getCarga())
                    .duracion(ejDto.getDuracion())
                    .build();
        }).collect(Collectors.toList());

        rutinaEjercicioRepo.saveAll(rutinaEjercicios);

        return RutinaDTO.builder()
                .idRutina(savedRutina.getIdRutina())
                .nombre(savedRutina.getNombre())
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
//            logger.info("Rutina: {} -> ejercicios: {}", rutina.getNombre(), ejercicios.size());
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
                                .repeticion(re.getRepeticiones())
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

    @Override
    @Transactional
    public void eliminarRutina(Integer idRutina) {
        try {
            logger.info("🟡 Verificando existencia de rutina ID: {}", idRutina);

            if (!rutinaRepo.existsById(idRutina)) {
                logger.warn("❌ Rutina no encontrada con ID: {}", idRutina);
                throw new RuntimeException("Rutina no encontrada con ID: " + idRutina);
            }

            logger.info("🗑️ Eliminando ejercicios asociados por SQL nativo...");
            rutinaEjercicioRepo.eliminarTodoPorRutina(idRutina);
            entityManager.flush();
            entityManager.clear();

            logger.info("✅ Ejercicios eliminados. Procediendo a eliminar rutina por ID directamente...");
            rutinaRepo.deleteById(idRutina);

            logger.info("✅ Rutina con ID {} eliminada correctamente.", idRutina);
        } catch (Exception e) {
            logger.error("❌ Fallo al eliminar rutina ID {}: {}", idRutina, e.getMessage(), e);
            throw new RuntimeException("Error crítico al eliminar rutina: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public RutinaDTO actualizarRutina(Integer id, RutinaDTO rutinaDTO) {
        Rutina rutina = rutinaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + id));

        rutina.setNombre(rutinaDTO.getNombre());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setFotoRutina(rutinaDTO.getFotoRutina());
        rutina.setEnfoque(rutinaDTO.getEnfoque());
        rutina.setDificultad(rutinaDTO.getDificultad());

        Rutina updatedRutina = rutinaRepo.save(rutina);

        // Actualizar los ejercicios asociados
        List<RutinaEjercicio> rutinaEjercicios = rutinaEjercicioRepo.findByRutina(updatedRutina);
        for (int i = 0; i < rutinaDTO.getEjercicios().size(); i++) {
            RutinaDTO.EjercicioDTO ejDto = rutinaDTO.getEjercicios().get(i);
            if (i < rutinaEjercicios.size()) {
                RutinaEjercicio re = rutinaEjercicios.get(i);
                re.setRepeticiones(ejDto.getRepeticion());
                re.setSeries(ejDto.getSeries());
                re.setCarga(ejDto.getCarga());
                re.setDuracion(ejDto.getDuracion());
                rutinaEjercicioRepo.save(re);
            } else {
                Ejercicio ejercicio = ejercicioRepo.findById(ejDto.getIdEjercicio())
                        .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejDto.getIdEjercicio()));
                RutinaEjercicio nuevaRutinaEjercicio = RutinaEjercicio.builder()
                        .rutina(updatedRutina)
                        .ejercicio(ejercicio)
                        .repeticiones(ejDto.getRepeticion())
                        .series(ejDto.getSeries())
                        .carga(ejDto.getCarga())
                        .duracion(ejDto.getDuracion())
                        .build();
                rutinaEjercicioRepo.save(nuevaRutinaEjercicio);
            }
        }

        return RutinaDTO.builder()
                .nombre(updatedRutina.getNombre())
                .descripcion(updatedRutina.getDescripcion())
                .fotoRutina(updatedRutina.getFotoRutina())
                .enfoque(updatedRutina.getEnfoque())
                .dificultad(Dificultad.valueOf(updatedRutina.getDificultad().name()))
                .build();

    }


}
