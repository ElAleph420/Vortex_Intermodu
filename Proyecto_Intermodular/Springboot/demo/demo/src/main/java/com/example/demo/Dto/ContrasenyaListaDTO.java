// src/main/java/com/example/demo/dto/ContrasenyaListaDTO.java
package com.example.demo.Dto;

import java.time.LocalDate;

public class ContrasenyaListaDTO {
    private String servicio;
    private String valor;
    private LocalDate fechaCreacion;
    private LocalDate fechaExpiracion;
    private LocalDate fechaCaducidadGestor;
    private String estado; // 'activa', 'expira-pronto', 'expira-hoy', 'expirada', 'gestor-caduca'

    // Constructor completo, getters y setters
    public ContrasenyaListaDTO(String servicio, String valor, LocalDate fechaCreacion, 
                               LocalDate fechaExpiracion, LocalDate fechaCaducidadGestor, String estado) {
        this.servicio = servicio;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
        this.fechaExpiracion = fechaExpiracion;
        this.fechaCaducidadGestor = fechaCaducidadGestor;
        this.estado = estado;
    }
    
    // Generar getters y setters habituales...
    public String getServicio() { return servicio; }
    public String getValor() { return valor; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public LocalDate getFechaCaducidadGestor() { return fechaCaducidadGestor; }
    public String getEstado() { return estado; }
}
