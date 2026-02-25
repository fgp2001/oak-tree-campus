package com.oaktree.campus.controller;

import com.oaktree.campus.model.*;
import com.oaktree.campus.model.enums.TipoUsuario;
import com.oaktree.campus.repository.*;
import com.oaktree.campus.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CursoRepository cursoRepository;
    private final AlumnoRepository alumnoRepository;
    private final ProfesorRepository profesorRepository;
    private final AlumnoCursoRepository alumnoCursoRepository;
    private final ProfesorCursoRepository profesorCursoRepository;
    private final TokenActivacionRepository tokenRepository;
    private final EmailService emailService;


    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        return "admin/cursos/list";
    }

    @GetMapping("/cursos/nuevo")
    public String nuevoCurso(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("profesores", profesorRepository.findAll());
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "admin/cursos/form";
    }

    @PostMapping("/cursos/guardar")
    @Transactional
    public String guardarCurso(@ModelAttribute Curso curso, @RequestParam(required = false) List<Long> profesoresIds,
                               @RequestParam(required = false) List<Long> alumnosIds) {
        Curso cursoGuardado = cursoRepository.save(curso);
        profesorCursoRepository.deleteByCurso(cursoGuardado);
        alumnoCursoRepository.deleteByCurso(cursoGuardado);

        //Asignacion del profesor
        if (profesoresIds != null) {
            for (Long id : profesoresIds) {
                Profesor profesor = profesorRepository.findById(id).orElseThrow();

                ProfesorCurso pc = new ProfesorCurso();
                pc.setCurso(cursoGuardado);
                pc.setProfesor(profesor);
                profesorCursoRepository.save(pc);
            }
        }

        //Asignar alumnos
        if (alumnosIds != null) {
            for (Long id : alumnosIds) {
                Alumno alumno = alumnoRepository.findById(id).orElseThrow();

                AlumnoCurso ac = new AlumnoCurso();
                ac.setCurso(cursoGuardado);
                ac.setAlumno(alumno);
                alumnoCursoRepository.save(ac);
            }
        }
        return "redirect:/admin/cursos";
    }

    @GetMapping("/cursos/editar/{id}")
    public String editarCurso(@PathVariable Long id, Model model) {

        Curso curso = cursoRepository.findById(id).orElseThrow();
        model.addAttribute("curso", curso);
        model.addAttribute("profesores", profesorRepository.findAll());
        model.addAttribute("alumnos", alumnoRepository.findAll());

        return "admin/cursos/form";
    }

    @GetMapping("/cursos/eliminar/{id}")
    @Transactional
    public String eliminarCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow();
        profesorCursoRepository.deleteByCurso(curso);
        alumnoCursoRepository.deleteByCurso(curso);
        cursoRepository.delete(curso);

        return "redirect:/admin/cursos";
    }

    @GetMapping("/alumnos/nuevo")
    public String nuevoAlumno(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "admin/alumnos/form";
    }

    @PostMapping("/alumnos/guardar")
    public String guardarAlumno(@ModelAttribute Alumno alumno) {
        alumno.setActivo(false);
        alumno.setPassword(null);
        alumno.setTipoUsuario(TipoUsuario.ALUMNO);
        alumno.setFechaCreacion(LocalDate.now());
        alumnoRepository.save(alumno);

        String token = UUID.randomUUID().toString();

        TokenActivacion tokenActivacion = new TokenActivacion();
        tokenActivacion.setToken(token);
        tokenActivacion.setUsuario(alumno);

        tokenRepository.save(tokenActivacion);

        emailService.enviarEmail(alumno.getEmail(), "Activacion de Cuenta", "Hace click para activar: http://localhost:8080/activar?token=" + token);

        return "redirect:/admin/cursos";
    }

    @GetMapping("/profesores/nuevo")
    public String nuevoProfesor(Model model) {
        model.addAttribute("profesor", new Profesor());
        return "admin/profesores/form";
    }

    @PostMapping("/profesores/guardar")
    public String guardarProfesor(@ModelAttribute Profesor profesor) {

        profesor.setPassword(null);
        profesor.setActivo(false);
        profesor.setTipoUsuario(TipoUsuario.PROFESOR);
        profesor.setFechaCreacion(LocalDate.now());
        profesorRepository.save(profesor);

        String token = UUID.randomUUID().toString();

        TokenActivacion tokenActivacion = new TokenActivacion();
        tokenActivacion.setToken(token);
        tokenActivacion.setUsuario(profesor);

        tokenRepository.save(tokenActivacion);

        emailService.enviarEmail(
                profesor.getEmail(),
                "Activación de cuenta",
                "Hacé clic para activar: http://localhost:8080/activar?token=" + token
        );

        return "redirect:/admin/cursos";
    }

    @GetMapping("/cursos/{id}/alumnos")
    @ResponseBody
    public List<Map<String, String>> obtenerAlumnos(@PathVariable Long id) {
        Curso curso = cursoRepository.findById(id).orElseThrow();

        return curso.getAlumnos().stream().map(ac -> Map.of(
                "nombre", ac.getAlumno().getNombre(),
                "email", ac.getAlumno().getEmail()
        )).sorted(Comparator.comparing(a-> a.get("nombre")))
                .toList();

    }


}
