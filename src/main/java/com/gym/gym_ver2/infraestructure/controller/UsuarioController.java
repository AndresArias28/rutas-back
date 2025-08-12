package com.gym.gym_ver2.infraestructure.controller;

import com.gym.gym_ver2.aplicaction.service.UsuarioService;
import com.gym.gym_ver2.infraestructure.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/registerPuntos/{idUsuario}")
    public ResponseEntity<String> registerPuntos(@PathVariable Integer idUsuario, @RequestBody Double puntos) {
        // Aquí puedes implementar la lógica para registrar puntos de usuario
        // Por ejemplo, podrías llamar a un servicio que maneje la lógica de negocio
        String resultado = userService.registerPuntos(idUsuario, puntos);
        return ResponseEntity.ok(resultado);
    }
}
