package com.example.demo.service;

import com.example.demo.model.Plan;
import com.example.demo.model.Usuario;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Random;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registrar(String dni, String nombre, String email, String planNombre) {
        // Generar contraseña tipo APX4 (3 mayúsculas + 1 número)
        Random r = new Random();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String contrasenaGenerada = "" +
            letras.charAt(r.nextInt(26)) +
            letras.charAt(r.nextInt(26)) +
            letras.charAt(r.nextInt(26)) +
            r.nextInt(10);

        // Buscar el plan
        Plan plan = planRepository.findAll().stream()
            .filter(p -> p.getTipoPlan().equalsIgnoreCase(planNombre))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Plan no encontrado: " + planNombre));

        // Crear usuario
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(contrasenaGenerada));
        usuario.setPlan(plan);
        usuario.setFechaRegistro(LocalDate.now());

        usuarioRepository.save(usuario);

        return contrasenaGenerada;
    }
}
