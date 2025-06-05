package com.gym.gym_ver2.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "desafio_realizado")
public class DesafioRealizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desafio_realizado")
    private Integer idDesafioRealizado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_desafio", referencedColumnName = "id_desafio")
    private Desafio desafio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rutina_ejercicio", referencedColumnName = "id_rutina_ejercicio")
    private RutinaEjercicio rutinaEjercicio;

    @Column(name = "fecha_inicio_desafio")
    private LocalDateTime inicioDesafio;

    @Column(name = "fecha_fin_desafio")
    private LocalDateTime finDesafio;

    @Column(name = "estado")
    private String estado;


}
