package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CONTRASENYA")
public class Contrasenya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrasenya")
    private Integer idContrasenya;

    @ManyToOne
    @JoinColumn(name = "dni_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "valor_cifrado", nullable = false, length = 255)
    private String valorCifrado;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_caducidad_gestor")
    private LocalDate fechaCaducidadGestor;

    public Integer getIdContrasenya() { return idContrasenya; }
    public void setIdContrasenya(Integer idContrasenya) { this.idContrasenya = idContrasenya; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getValorCifrado() { return valorCifrado; }
    public void setValorCifrado(String valorCifrado) { this.valorCifrado = valorCifrado; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDate getFechaCaducidadGestor() { return fechaCaducidadGestor; }
    public void setFechaCaducidadGestor(LocalDate fechaCaducidadGestor) { this.fechaCaducidadGestor = fechaCaducidadGestor; }
}