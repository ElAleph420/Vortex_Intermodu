package com.example.demo.controller;

import com.example.demo.Dto.*;
import com.example.demo.service.ContrasenyaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contrasenya")
public class ContrasenyaApiController {

    @Autowired
    private ContrasenyaService contrasenyaService;

    @GetMapping("/generar")
    public ResponseEntity<?> generar(
            @RequestParam int longitud,
            @RequestParam(defaultValue = "false") boolean mayusculas,
            @RequestParam(defaultValue = "false") boolean minusculas,
            @RequestParam(defaultValue = "false") boolean numeros,
            @RequestParam(defaultValue = "false") boolean especiales,
            @RequestParam(required = false) String palabra) {

        String pass = contrasenyaService.generarContrasenyaSegura(
                longitud, mayusculas, minusculas, numeros, especiales, palabra);
        return ResponseEntity.ok(Map.of("contrasenya", pass));
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody GuardarContrasenyaDTO dto, Authentication auth) {
        try {
            contrasenyaService.guardarContrasenya(dto, auth.getName());
            return ResponseEntity.ok(Map.of("msg", "Contraseña guardada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<?> lista(Authentication auth) {
        try {
            List<ContrasenyaListaDTO> lista = contrasenyaService.listarParaUsuario(auth.getName());
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/plan")
    public ResponseEntity<?> plan(Authentication auth) {
        try {
            String plan = contrasenyaService.obtenerPlanUsuario(auth.getName());
            return ResponseEntity.ok(Map.of("plan", plan));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}