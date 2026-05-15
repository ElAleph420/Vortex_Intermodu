package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class ContrasenyaServicioId implements Serializable {
    private Integer contrasenya;
    private Integer servicio;

    public ContrasenyaServicioId() {}
    public ContrasenyaServicioId(Integer contrasenya, Integer servicio) {
        this.contrasenya = contrasenya;
        this.servicio = servicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContrasenyaServicioId)) return false;
        ContrasenyaServicioId that = (ContrasenyaServicioId) o;
        return Objects.equals(contrasenya, that.contrasenya) && Objects.equals(servicio, that.servicio);
    }

    @Override
    public int hashCode() { return Objects.hash(contrasenya, servicio); }
}