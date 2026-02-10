package com.oaktree.campus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "alumnos")
@Getter@Setter
public class Alumno extends Usuario{
    private String legajo;
}
