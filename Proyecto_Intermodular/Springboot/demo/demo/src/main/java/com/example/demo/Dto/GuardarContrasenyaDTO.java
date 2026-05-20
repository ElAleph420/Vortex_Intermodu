package com.example.demo.Dto; // O "dto" en minúsculas, según tu carpeta

import java.time.LocalDate;

public class GuardarContrasenyaDTO { // Debe coincidir con el nombre del archivo
    private String valor;
    private String nombreServicio; 
    private LocalDate fechaExpiracion;

    // Getters y Setters
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public LocalDate getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(LocalDate fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
}