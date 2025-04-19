package com.isaacAnco.inventory.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://tudominio.com")); // Orígenes permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos HTTP permitidos
        config.setAllowedHeaders(List.of("*")); // Headers permitidos (Authorization, Content-Type, etc.)
        config.setAllowCredentials(true); // Habilita cookies y autenticación (necesario para JWT)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todas las rutas
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (incluyendo Swagger)
                        .requestMatchers(
                                "/",
                                "/api/**",
                                "/swagger-ui.html",       // Swagger UI HTML
                                "/swagger-ui/**",        // Swagger UI recursos (JS, CSS)
                                "/v3/api-docs",           // Documentación OpenAPI (JSON)
                                "/v3/api-docs/**",        // Otras rutas de OpenAPI
                                "/webjars/**",           // Recursos web (si Swagger los usa)
                                "/swagger-resources/**",  // Recursos de Swagger
                                "/configuration/**"       // Configuración de Swagger
                        ).permitAll()
                        .anyRequest().authenticated()  // El resto requiere autenticación
                );
        return http.build();
    }
}