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
    String apellidos;
    String nombres;
    String identificacion;

}
