package com.oaktree.campus.config;

import com.oaktree.campus.model.Admin;
import com.oaktree.campus.model.Alumno;
import com.oaktree.campus.model.enums.TipoUsuario;
import com.oaktree.campus.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        return args -> {

            String emailAdmin = "admin@campusoaktree.com";

            if(usuarioRepo.findByEmail(emailAdmin).isEmpty()) {
                Admin admin = new Admin()   ;
                admin.setNombre("Admin");
                admin.setEmail(emailAdmin);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setActivo(true);
                admin.setFechaCreacion(LocalDate.now());
                admin.setTipoUsuario(TipoUsuario.ADMIN);

                usuarioRepo.save(admin);

            }

            if (usuarioRepo.findByEmail("juan@mail.com").isEmpty()) {

                Alumno alumno = new Alumno();
                alumno.setNombre("Juan");
                alumno.setEmail("juan@mail.com");
                alumno.setPassword(passwordEncoder.encode("123456"));
                alumno.setActivo(true);
                alumno.setTipoUsuario(TipoUsuario.ALUMNO);
                alumno.setFechaCreacion(LocalDate.now());

                usuarioRepo.save(alumno);
            }
        };
    }
}
