package com.gym.gym_ver2.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PuntosHistorial extends JpaRepository<com.gym.gym_ver2.domain.model.entity.PuntosHistorial,Integer> {

    Optional<Object> findByIdUsuario(Integer idUsuario);
}
