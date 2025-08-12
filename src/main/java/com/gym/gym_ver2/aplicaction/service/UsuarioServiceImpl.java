package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.entity.Rol;
import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.infraestructure.exceptions.RecursoNoEncontradoException;
import com.gym.gym_ver2.infraestructure.repository.PuntosHistorial;
import com.gym.gym_ver2.infraestructure.repository.RolRepository;
import com.gym.gym_ver2.infraestructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements  UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PuntosHistorial puntosHistorialRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PuntosHistorial puntosHistorialRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.puntosHistorialRepository = puntosHistorialRepository;
    }

    @Transactional
    @Override
    public Usuario upsertFromGoogle(String email, String nombre) {
       Rol rolUser = rolRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol 'usuario' no encontrado"));

        return usuarioRepository.findByEmailUsuario(email)
                .map(usuarioExistente -> {
                    // Si el nombre cambió, lo actualizamos
                    if (nombre != null && !nombre.equals(usuarioExistente.getNombreUsuario())) {
                        usuarioExistente.setNombreUsuario(nombre);
                    }
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseGet(() -> {
                    // Crear usuario nuevo para login con Google
                    Usuario nuevo = new Usuario();
                    nuevo.setEmailUsuario(email);
                    nuevo.setNombreUsuario(nombre);
                    nuevo.setContrasenaUsuario("");
                    nuevo.setIdRol(rolUser);
                    return usuarioRepository.save(nuevo);
                });
    }

    @Override
    public String registerPuntos(Integer idUsuario, Double puntos) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        // Aquí podrías agregar la lógica para registrar puntos, por ejemplo:
         usuario.setPuntos(puntos);
         puntosHistorialRepository.findByIdUsuario(idUsuario);
         //todo; aqui quede
        return " Puntos registrados para el usuario: " + usuario.getNombreUsuario() + " con ID: " + idUsuario;
    }

}
