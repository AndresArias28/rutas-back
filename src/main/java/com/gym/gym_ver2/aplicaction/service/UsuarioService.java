package com.gym.gym_ver2.aplicaction.service;

import com.gym.gym_ver2.domain.model.entity.Usuario;
import com.gym.gym_ver2.domain.model.pojos.UserResponse;
import com.gym.gym_ver2.domain.model.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> getUsers();

    UsuarioDTO getUser(Integer idPersona);

    UserResponse actualizarUsuario(UsuarioDTO userRequest);

    void updatePassword(String email, String newPassword);
}
