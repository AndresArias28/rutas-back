package com.gym.gym_ver2.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id_persona")
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "email_usuario"),  @UniqueConstraint(columnNames = "contrasena_usuario")})
public class Usuario extends Persona implements UserDetails  {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_usuario")
//    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    private Rol idRol;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona", nullable = false)
//    private Integer idPersona;

    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "estado_usuario")
    private String estadoUsuario;

    /*
         @Column(name="apellido_usuario")
    private String apellidoUsuario;



    @Column(name="fecha_nacimineto")
    private Date fechaNacimiento;

    @Column(name = "estatura_usuario")
    private Double estaturaUsuario;

    @Column(name = "peso_usuario")
    private Double pesoUsuario;

    @Column(name = "puntos_acumulados_usuario")
    private Integer puntosUsuario;

    @Column(name = "numero_ficha_usuario")
    private Integer numeroFicha;

    @Column(name = "recompensa_horas_usuario")
    private Integer horasRecompensas;

    @Column(name = "nivel_actual_usuario")
    private Integer nivelActualUsuario;
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return idRol != null
                ? List.of(new SimpleGrantedAuthority("ROLE_"+idRol.getNombreRol())) // Convierte el rol único en una lista con un solo elemento
                : List.of(); // Si no hay rol, devuelve una lista vacía
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
