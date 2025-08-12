package com.gym.gym_ver2.infraestructure.repository;

import com.gym.gym_ver2.domain.model.entity.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutasRepository  extends JpaRepository<Ruta, Long> {
}
