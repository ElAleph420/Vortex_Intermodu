package com.example.demo.config;

import com.example.demo.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. TU MÉTODO ORIGINAL: Encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. TU MÉTODO ORIGINAL: Gestor de autenticación vinculado a tu UsuarioDetailsService
    @Bean
    public AuthenticationManager authenticationManager(UsuarioDetailsService uds) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(uds);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    // 3. EL FILTRO DE SEGURIDAD: Tu lógica original + las mejoras de la API y CSRF
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        http
            // CONECTA TU AUTH MANAGER ORIGINAL
            .authenticationManager(authManager)
            
            // NUEVO: Desactiva CSRF para que las llamadas fetch (JavaScript) puedan hacer POST a la base de datos
            .csrf(csrf -> csrf.disable())
            
            // CONTROL DE ACCESOS
            .authorizeHttpRequests(auth -> auth
                // Tus rutas públicas originales
                .requestMatchers("/", "/login", "/auth/**", "/registro/**", "/logout", "/error", "/css/**", "/logo.png", "/*.png", "/*.ico").permitAll()
                
                // NUEVO: Asegura que los endpoints del gestor asíncrono requieran sesión activa
                .requestMatchers("/api/contrasenya/**").authenticated()
                
                // Cualquier otra ruta (como /dashboard) requiere estar logueado
                .anyRequest().authenticated()
            )
            
            // TU FORM LOGIN ORIGINAL
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            
            // TU LOGOUT ORIGINAL
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
            
        return http.build();
    }
}