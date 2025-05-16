package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.aplicaction.service.RutinaService;
import com.gym.gym_ver2.domain.model.dto.RutinaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rutina")
@RestController
@RequiredArgsConstructor
public class RutinaController {

    private final RutinaService rutinaService;

    @PostMapping("/crear")
    public ResponseEntity<RutinaDTO> crearRutina(@RequestBody RutinaDTO rutinaDTO) {
        try {
            RutinaDTO nuevaRutina = rutinaService.crearRutina(rutinaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRutina);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
