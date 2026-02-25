package com.oaktree.campus.controller;

import com.oaktree.campus.model.TokenActivacion;
import com.oaktree.campus.model.Usuario;
import com.oaktree.campus.repository.TokenActivacionRepository;
import com.oaktree.campus.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final TokenActivacionRepository tokenRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenActivacionRepository tokenRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/activar")
    public String mostrarFormularioActivacion(@RequestParam String token, Model model) {
        TokenActivacion tokenActivacion = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token Inválido o expirado"));

        if (tokenActivacion.getUsuario().isActivo()){
            return "redirect:/login?yaActivado";
        }
        model.addAttribute("token", token);
        return "auth/activar";
    }

    @PostMapping("/activar")
    public String activarCuenta(@RequestParam String token, @RequestParam String password){
        TokenActivacion tokenActivacion = tokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("Token Inválido o expirado"));

        Usuario usuario = tokenActivacion.getUsuario();

        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setActivo(true);

        usuarioRepository.save(usuario);
        tokenRepository.delete(tokenActivacion);

        return "redirect:/login?activado";
    }

}
