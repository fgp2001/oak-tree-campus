package com.oaktree.campus.model;

import com.oaktree.campus.model.enums.EstadoCuota;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "alumnos_cursos")
@Getter
@Setter
public class AlumnoCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private EstadoCuota estadoCuota;

    private LocalDate fechaAsignacion;

    @PrePersist
    public void prePersist(){
        this.fechaAsignacion = LocalDate.now();
        if (this.estadoCuota == null){
            this.estadoCuota = EstadoCuota.AL_DIA;
        }
    }

}
