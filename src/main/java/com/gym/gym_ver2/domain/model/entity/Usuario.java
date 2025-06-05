package com.gym.gym_ver2.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = "email_usuario"),  @UniqueConstraint(columnNames = "contrasena_usuario")})
public class Usuario implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    private Rol idRol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesafioRealizado> desafioRealizados;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @Column(name="nombre_usuario")
    private String nombreUsuario;

    @Column(name = "contrasena_usuario")
    private String contrasenaUsuario;

    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "estado_usuario")
    private String estado;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

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
