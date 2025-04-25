//package com.gym.gym_ver2.domain.model.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.math.BigDecimal;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "aprendiz")
//@PrimaryKeyJoinColumn(name = "id_persona")
//public class Aprendiz extends Persona {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_aprendiz")
//    private Integer idAprendiz;
//
//    @Column(name = "ficha")
//    private Integer ficha;
//
//    private String jornada;
//
//    private BigDecimal estatura;
//
//    private BigDecimal peso;
//
//    @Column(name = "puntos_acumulados")
//    private Integer puntosAcumulados;
//
//    @Column(name = "horas_acumuladas")
//    private Integer horasAcumuladas;
//
//    @Column(name = "foto_perfil")
//    private String fotoPerfil;
//}
