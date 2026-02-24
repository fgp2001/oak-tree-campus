package com.oaktree.campus.repository;

import com.oaktree.campus.model.Curso;
import com.oaktree.campus.model.Profesor;
import com.oaktree.campus.model.ProfesorCurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesorCursoRepository extends JpaRepository<ProfesorCurso, Long> {

    boolean existsByProfesorAndCurso(Profesor profesor, Curso curso);
    List<ProfesorCurso> findByCurso(Curso curso);
    void deleteByCurso(Curso curso);
}
