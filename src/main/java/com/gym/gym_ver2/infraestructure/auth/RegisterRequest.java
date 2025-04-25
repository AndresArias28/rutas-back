package com.gym.gym_ver2.infraestructure.auth;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String nombreUsuario;
    String apellidos;
    String nombres;
    String telefono;
    String identificacion;
    Date fechaNacimiento;
    String contrasenaUsuario;
    String emailUsuario;
    BigDecimal estatura;
    BigDecimal peso;
    Integer ficha;
    String jornada;
    String fotoPerfil;
    String estado;
    Integer horasAcumuladas;
    Integer puntosAcumulados;
}
