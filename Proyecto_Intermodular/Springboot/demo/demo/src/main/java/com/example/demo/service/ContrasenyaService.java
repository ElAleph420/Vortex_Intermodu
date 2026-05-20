package com.example.demo.service;

import com.example.demo.Dto.GuardarContrasenyaDTO;
import com.example.demo.Dto.ContrasenyaListaDTO;
import com.example.demo.Dto.ContrasenyaListaDTO;
import com.example.demo.Dto.GuardarContrasenyaDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
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

    private static final String MAYUS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMS = "0123456789";
    private static final String ESPS = "!@#$%^&*()-_=+[{]};:,.<>?";

    public String generarContrasenyaSegura(int longitud, boolean m, boolean mi, boolean n, boolean e, String palabra) {
        StringBuilder pool = new StringBuilder();
        
        if (m) pool.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (mi) pool.append("abcdefghijklmnopqrstuvwxyz");
        if (n) pool.append("0123456789");
        if (e) pool.append("!@#$%^&*()-_=+[{]};:,.<>?");
        
        // Si no hay selección, ponemos algo por defecto para evitar blancos
        if (pool.length() == 0) pool.append("abcdefghijklmnopqrstuvwxyz0123456789");

        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder resultado = new StringBuilder();

        // Generamos los caracteres
        for (int i = 0; i < longitud; i++) {
            resultado.append(pool.charAt(random.nextInt(pool.length())));
        }

        // Insertamos la palabra si existe
        if (palabra != null && !palabra.isEmpty() && longitud > palabra.length()) {
            int pos = random.nextInt(longitud - palabra.length() + 1);
            resultado.replace(pos, pos + palabra.length(), palabra);
        }

        return resultado.toString();
    }

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
                if (diasRestantes < 0) {
                    estado = "expirada";
                } else if (diasRestantes <= 1) {
                    estado = "expira-hoy"; 
                } else if (diasRestantes <= 10) {
                    estado = "expira-pronto"; 
                }
            }
            
            String nombreServicioLabel = (cs.getServicio() != null) ? cs.getServicio().getNombreServicio() : "General";

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

    public void guardarContrasenya(GuardarContrasenyaDTO dto, String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardarContrasenya'");
    }
}