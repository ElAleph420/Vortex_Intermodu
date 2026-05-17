package com.example.demo.controller;

import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String registro(@RequestParam String plan, Model model) {
        model.addAttribute("plan", plan);
        return "registro";
    }

    @PostMapping("/registro/procesar")
    public String procesar(
            @RequestParam String dni,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String plan,
            Model model) {

        String contrasenaGenerada = usuarioService.registrar(dni, nombre, email, plan);
        model.addAttribute("contrasena", contrasenaGenerada);
        model.addAttribute("email", email);
        return "registro-exito";
    }
}