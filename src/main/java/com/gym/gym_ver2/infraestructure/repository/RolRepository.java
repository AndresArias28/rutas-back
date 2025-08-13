package com.gym.gym_ver2.infraestructure.repository;

import com.gym.gym_ver2.domain.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(String administrador);
}
