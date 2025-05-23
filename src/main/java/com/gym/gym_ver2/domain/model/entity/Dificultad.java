package com.gym.gym_ver2.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Dificultad {
    PRINCIPIANTE("Principiante"),
    INTERMEDIO("Intermedio"),
    AVANZADO("Avanzado");

    private final String valor;

    Dificultad(String valor) {
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

    @JsonCreator
    public static Dificultad from(String input) {
        for (Dificultad d : values()) {
            if (d.valor.equalsIgnoreCase(input) || d.name().equalsIgnoreCase(input)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Dificultad no válida: " + input);
    }
}
