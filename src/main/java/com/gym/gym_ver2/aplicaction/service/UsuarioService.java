package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.entity.Usuario;

public interface UsuarioService {

//    List<UsuarioDTO> getUsers();
//
//    UsuarioDTO getUser(Integer idPersona);
//
//    UserResponse actualizarUsuario(UsuarioDTO userRequest);
//
//    void updatePassword(String email, String newPassword);

    Usuario upsertFromGoogle(String email, String name);

    String registerPuntos(Integer idUsuario, Double puntos);
}
