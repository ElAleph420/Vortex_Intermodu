package com.example.demo.repository;

import com.example.demo.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    Optional<Servicio> findByNombreServicio(String nombreServicio);
}