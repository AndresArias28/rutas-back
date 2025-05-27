package com.gym.gym_ver2.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "desafio")
public class Desafio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desafio")
    private Integer idDesafio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rutina", referencedColumnName = "id_rutina")
    private Rutina idRutina;

    @Column(name = "descripcion_desafio")
    private String descripcionDesafio;

    @Column(name = "puntuaje_desafio")
    private Integer puntuajeDesafio;

    @Column(name = "foto_desafio")
    private String fotoDesafio;

    @OneToMany(mappedBy = "idDesafio", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DesafioRealizado> desafioRealizados;


}
