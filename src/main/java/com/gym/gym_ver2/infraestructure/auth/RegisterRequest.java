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
    String emailUsuario;
    String contrasenaUsuario;
    String estado;
    String fotoPerfil;

    String apellidos;
    String nombres;
    String telefono;
    String identificacion;
    Date fechaNacimiento;
    String sexo;

    BigDecimal estatura;
    BigDecimal peso;
    Integer ficha;
    String jornada;
    Integer horasAcumuladas;
    Integer puntosAcumulados;
    String nivelFisico;

}
