// src/main/java/com/example/demo/repository/ContrasenyaServicioRepository.java
package com.example.demo.repository;

import com.example.demo.model.ContrasenyaServicio;
import com.example.demo.model.ContrasenyaServicioId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContrasenyaServicioRepository extends JpaRepository<ContrasenyaServicio, ContrasenyaServicioId> {
    // Buscar todas las relaciones de contraseñas que pertenezcan a un DNI de usuario concreto
    List<ContrasenyaServicio> findByContrasenyaUsuarioDni(String dni);
    
    // Contar cuántas contraseñas tiene ya guardadas un usuario para validar límites del plan
    long countByContrasenyaUsuarioDni(String dni);
}
