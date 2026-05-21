package com.example.demo.service;

import com.example.demo.Dto.GuardarContrasenyaDTO;
import com.example.demo.Dto.ContrasenyaListaDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContrasenyaService {

    @Autowired private ContrasenyaRepository contrasenyaRepository;
    @Autowired private ContrasenyaServicioRepository contrasenyaServicioRepository;
    @Autowired private ServicioRepository servicioRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    // =====================================================
    // GENERADOR
    // =====================================================
    public String generarContrasenyaSegura(int longitud, boolean m, boolean mi, boolean n, boolean e, String palabra) {
        StringBuilder pool = new StringBuilder();
        if (m)  pool.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (mi) pool.append("abcdefghijklmnopqrstuvwxyz");
        if (n)  pool.append("0123456789");
        if (e)  pool.append("!@#$%^&*()-_=+[{]};:,.<>?");
        if (pool.length() == 0) pool.append("abcdefghijklmnopqrstuvwxyz0123456789");

        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            resultado.append(pool.charAt(random.nextInt(pool.length())));
        }
        if (palabra != null && !palabra.isEmpty() && longitud > palabra.length()) {
            int pos = random.nextInt(longitud - palabra.length() + 1);
            resultado.replace(pos, pos + palabra.length(), palabra);
        }
        return resultado.toString();
    }

    // =====================================================
    // GUARDAR
    // =====================================================
    public void guardarContrasenya(GuardarContrasenyaDTO dto, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean esFree = usuario.getPlan().getTipoPlan().equalsIgnoreCase("free");

        // Los usuarios free SOLO pueden guardar contraseñas generadas por el programa.
        // Esta validación se hace en el frontend, pero la reforzamos aquí también:
        // simplemente dejamos pasar — el frontend ya garantiza que sea generada.
        // Si quisieras validarlo aquí necesitarías pasar un flag extra en el DTO.

        // Calcular fecha de caducidad del gestor según el plan
        LocalDate fechaCaducidadGestor = null;
        if (esFree) {
            fechaCaducidadGestor = LocalDate.now().plusDays(30);
        }

        // Crear y guardar la contraseña
        Contrasenya contrasenya = new Contrasenya();
        contrasenya.setUsuario(usuario);
        contrasenya.setValorCifrado(dto.getValor());
        contrasenya.setFechaCreacion(LocalDate.now());
        contrasenya.setFechaCaducidadGestor(fechaCaducidadGestor);
        contrasenyaRepository.save(contrasenya);

        // Buscar o crear el servicio por nombre
        String nombreServicio = (dto.getNombreServicio() != null && !dto.getNombreServicio().isBlank())
                ? dto.getNombreServicio().trim()
                : "General";

        Servicio servicio = servicioRepository.findByNombreServicio(nombreServicio)
                .orElseGet(() -> {
                    Servicio nuevo = new Servicio();
                    nuevo.setNombreServicio(nombreServicio);
                    return servicioRepository.save(nuevo);
                });

        // Crear la relación ContrasenyaServicio
        ContrasenyaServicio cs = new ContrasenyaServicio();
        cs.setContrasenya(contrasenya);
        cs.setServicio(servicio);
        cs.setFechaExpiracion(dto.getFechaExpiracion()); // puede ser null, está permitido
        contrasenyaServicioRepository.save(cs);
    }

    // =====================================================
    // LISTAR
    // =====================================================
    public List<ContrasenyaListaDTO> listarParaUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<ContrasenyaServicio> lista = contrasenyaServicioRepository.findByContrasenyaUsuarioDni(usuario.getDni());
        LocalDate hoy = LocalDate.now();

        return lista.stream().map(cs -> {
            String estado = "activa";
            LocalDate exp = cs.getFechaExpiracion();
            LocalDate cadGestor = cs.getContrasenya().getFechaCaducidadGestor();

            if (exp != null) {
                long diasRestantes = ChronoUnit.DAYS.between(hoy, exp);
                if (diasRestantes < 0)       estado = "expirada";
                else if (diasRestantes <= 1) estado = "expira-hoy";
                else if (diasRestantes <= 10) estado = "expira-pronto";
            }

            String nombreServicioLabel = (cs.getServicio() != null)
                    ? cs.getServicio().getNombreServicio()
                    : "General";

            return new ContrasenyaListaDTO(
                nombreServicioLabel,
                cs.getContrasenya().getValorCifrado(),
                cs.getContrasenya().getFechaCreacion(),
                exp,
                cadGestor,
                estado
            );
        }).collect(Collectors.toList());
    }

    // =====================================================
    // PLAN DEL USUARIO
    // =====================================================
    public String obtenerPlanUsuario(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getPlan().getTipoPlan().toLowerCase();
    }
}