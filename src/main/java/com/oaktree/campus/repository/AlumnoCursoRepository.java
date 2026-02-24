package com.oaktree.campus.repository;

import com.oaktree.campus.model.Alumno;
import com.oaktree.campus.model.AlumnoCurso;
import com.oaktree.campus.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoCursoRepository extends JpaRepository<AlumnoCurso, Long> {

    boolean existsByAlumnoAndCurso(Alumno alumno, Curso curso);
    List<AlumnoCurso> findByCurso(Curso curso);
    void deleteByCurso(Curso curso);
}
