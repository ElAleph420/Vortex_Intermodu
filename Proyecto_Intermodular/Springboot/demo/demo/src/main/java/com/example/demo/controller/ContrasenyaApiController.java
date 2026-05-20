package com.example.demo.controller;

import com.example.demo.Dto.*;
import com.example.demo.service.ContrasenyaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map; // Para el HashMap

@RestController
@RequestMapping("/api/contrasenya")
public class ContrasenyaApiController {

    @Autowired
    private ContrasenyaService contrasenyaService;

    // --- ESTO ES LO QUE TE FALTA Y POR LO QUE DA 404 ---
    @GetMapping("/generar")
    public ResponseEntity<?> generar(
            @RequestParam int longitud,
            @RequestParam(name = "mayusculas", defaultValue = "false") boolean mayusculas,
            @RequestParam(name = "minusculas", defaultValue = "false") boolean minusculas,
            @RequestParam(name = "numeros", defaultValue = "false") boolean numeros,
            @RequestParam(name = "especiales", defaultValue = "false") boolean especiales,
            @RequestParam(required = false) String palabra) {
        
        // Llamada al servicio
        String pass = contrasenyaService.generarContrasenyaSegura(longitud, mayusculas, minusculas, numeros, especiales, palabra);
        
        // Respuesta exacta para tu dashboard.html
        return ResponseEntity.ok(java.util.Map.of("contrasenya", pass));
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody GuardarContrasenyaDTO dto, Authentication auth) {
        try {
            contrasenyaService.guardarContrasenya(dto, auth.getName());
            return ResponseEntity.ok().body("{\"msg\": \"Contraseña guardada con éxito\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}