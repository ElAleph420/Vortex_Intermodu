package com.example.demo.repository;

import com.example.demo.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    /**
     * Busca un servicio por su nombre ignorando si es mayúscula o minúscula.
     * Esto permite que "Netflix" y "netflix" se consideren el mismo servicio.
     */
    Optional<Servicio> findByNombreServicioIgnoreCase(String nombreServicio);
    
}