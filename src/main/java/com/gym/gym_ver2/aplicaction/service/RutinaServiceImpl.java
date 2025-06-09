package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.RutinaDTO;
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

        int puntaje = switch (rutinaDTO.getDificultad()) {
            case PRINCIPIANTE -> 100;
            case INTERMEDIO   -> 200;
            case AVANZADO     -> 300;
        };

        // 1. Crear y guardar la rutina
        Rutina rutina = Rutina.builder()
                .nombre(rutinaDTO.getNombre())
                .descripcion(rutinaDTO.getDescripcion())
                .fotoRutina(rutinaDTO.getFotoRutina())
                .enfoque(rutinaDTO.getEnfoque())
                .dificultad(rutinaDTO.getDificultad())
                .puntuajeRutina(puntaje)
                .build();

        Rutina savedRutina = rutinaRepo.save(rutina);

        // 2. Mapear y guardar los ejercicios asociados a la rutina
        try {
            List<RutinaEjercicio> rutinaEjercicios = rutinaDTO.getEjercicios().stream().map(ejDto -> {
                // Verificar si el ejercicio existe
                Ejercicio ejercicio = ejercicioRepo.findById(ejDto.getIdEjercicio())
                        .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejDto.getIdEjercicio()));

                // Crear RutinaEjercicio y asociarlo a la rutina y ejercicio
                return RutinaEjercicio.builder()
                        .rutina(savedRutina)
                        .ejercicio(ejercicio)
                        .series(ejDto.getSeries())
                        .repeticiones(ejDto.getRepeticion())
                        .carga(ejDto.getCarga())
                        .duracion(ejDto.getDuracion())
                        .calorias(0) // Inicializar calorías en 0
                        .build();
            }).collect(Collectors.toList());

            System.out.println("Total de RutinaEjercicio generados: " + rutinaEjercicios.size());

            rutinaEjercicioRepo.saveAll(rutinaEjercicios);
            System.out.println("✅ RutinaEjercicios guardados correctamente.");

            // 3. Mapear los ejercicios al DTO interno
            List<RutinaDTO.RutinaEjercicioDTO> ejercicioDTOs = rutinaEjercicios.stream().map(re -> {
                Ejercicio ej = re.getEjercicio();
               // double met = ej.getMet() != null ? ej.getMet() : 0.0; // manejo de MET, si es nulo se asigna 1
                // calcular calorías basadas en MET, series, repeticiones y duración
//                double duracionSegundos = re.getDuracion() != null ? re.getDuracion() : 0;
//                double duracionHoras = duracionSegundos / 3600.0; // convertir a horas
//                int calorias = (int) Math.round(met * re.getSeries() * re.getRepeticiones() * duracionHoras);

                return RutinaDTO.RutinaEjercicioDTO.builder()
                        .idEjercicio(ej.getIdEjercicio())
                        .descripcion(ej.getDescripcionEjercicio())
                        .fotoEjercicio(ej.getFotoEjercicio())
                        .musculos(ej.getMusculos())
                        .series(re.getSeries())
                        .repeticion(re.getRepeticiones())
                        .carga(re.getCarga())
                        .duracion(re.getDuracion())
                        .build();
            }).collect(Collectors.toList());

            // 4. Retornar la rutina con ejercicios incluidos
            return RutinaDTO.builder()
                    .idRutina(savedRutina.getIdRutina())
                    .nombre(savedRutina.getNombre())
                    .descripcion(savedRutina.getDescripcion())
                    .fotoRutina(savedRutina.getFotoRutina())
                    .enfoque(savedRutina.getEnfoque())
                    .dificultad(savedRutina.getDificultad())
                    .puntuajeRutina(savedRutina.getPuntuajeRutina())
                    .ejercicios(ejercicioDTOs)
                    .build();

        } catch (Exception e) {
            System.err.println("Error al guardar RutinaEjercicios:");
            e.printStackTrace();
        }
        throw new RuntimeException("Error al crear rutina: " + rutinaDTO.getNombre());
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
            List<RutinaDTO.RutinaEjercicioDTO> ejercicioDTOs = ejercicios.stream()
                    .map( re -> {
                        Ejercicio ejercicio = re.getEjercicio();
                        return RutinaDTO.RutinaEjercicioDTO.builder()
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
            logger.info("Verificando existencia de rutina ID: {}", idRutina);

            if (!rutinaRepo.existsById(idRutina)) {
                logger.warn("Rutina no encontrada con ID: {}", idRutina);
                throw new RuntimeException("Rutina no encontrada con ID: " + idRutina);
            }

            logger.info("Eliminando ejercicios asociados por SQL nativo...");
            rutinaEjercicioRepo.eliminarTodoPorRutina(idRutina);
            entityManager.flush();
            entityManager.clear();

            logger.info("Ejercicios eliminados. Procediendo a eliminar rutina por ID directamente...");
            rutinaRepo.deleteById(idRutina);

            logger.info("Rutina con ID {} eliminada correctamente.", idRutina);
        } catch (Exception e) {
            logger.error("Fallo al eliminar rutina ID {}: {}", idRutina, e.getMessage(), e);
            throw new RuntimeException("Error crítico al eliminar rutina: " + e.getMessage());
        }
    }

    @Override
    public RutinaDTO actualizarRutina(Integer id, RutinaDTO rutinaDTO) {
        return null;
    }

}
