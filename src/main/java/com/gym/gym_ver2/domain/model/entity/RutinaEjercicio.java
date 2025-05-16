package com.gym.gym_ver2.domain.model.entity;

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
@Table(name = "rutinasejercicios")
public class RutinaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina_ejercicio")
    private Integer idRutinaEjercicio;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "id_rutina", referencedColumnName = "id_rutina")
    private Rutina idRutina;

    @ManyToOne(fetch =  FetchType.EAGER )
    @JoinColumn(name = "id_ejercicio", referencedColumnName = "id_ejercicio")
    private Ejercicio idEjercicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "dificultad")
    private Dificultad dificultad;

    @Column(name = "repeticiones")
    private Integer repeticiones;

    @Column(name = "series")
    private Integer series;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "carga")
    private Integer carga;




}
