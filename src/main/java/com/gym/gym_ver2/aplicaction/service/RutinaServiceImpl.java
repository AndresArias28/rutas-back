package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.dto.RutinaDTO;
import com.gym.gym_ver2.domain.model.entity.Rutina;
import com.gym.gym_ver2.infraestructure.repository.RutinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RutinaServiceImpl implements  RutinaService {

    private final RutinaRepository rutinaRepo;


    @Override
    @Transactional
    public RutinaDTO crearRutina(RutinaDTO rutinaDTO) {
        Rutina rutina = Rutina.builder()
                .nombre(rutinaDTO.getNombre())
                .descripcion(rutinaDTO.getDescripcion())
                .fotoRutina(rutinaDTO.getFotoRutina())
                .enfoque(rutinaDTO.getEnfoque())
                .build();

        Rutina savedRutina = rutinaRepo.save(rutina);

        return RutinaDTO.builder()
                .nombre(savedRutina.getNombre())
                .descripcion(savedRutina.getDescripcion())
                .fotoRutina(savedRutina.getFotoRutina())
                .enfoque(savedRutina.getEnfoque())
                .build();
    }
}
