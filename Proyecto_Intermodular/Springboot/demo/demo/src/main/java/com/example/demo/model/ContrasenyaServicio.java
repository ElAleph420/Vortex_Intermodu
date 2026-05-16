package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CONTRASENYA_SERVICIO")
@IdClass(ContrasenyaServicioId.class)
public class ContrasenyaServicio {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_contrasenya", nullable = false)
    private Contrasenya contrasenya;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    public Contrasenya getContrasenya() { return contrasenya; }
    public void setContrasenya(Contrasenya contrasenya) { this.contrasenya = contrasenya; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}