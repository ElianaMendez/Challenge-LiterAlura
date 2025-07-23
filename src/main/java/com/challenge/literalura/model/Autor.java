package com.challenge.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer anio_nacimiento;
    private Integer anio_fallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.anio_nacimiento = datosAutor.anio_nacimiento();
        this.anio_fallecimiento = datosAutor.anio_fallecimiento();
    }

    @Override
    public String toString() {
        return "Autor: " + nombre +
                ", Año de Nacimiento: " + anio_nacimiento +
                ", Año de Fallecimiento: " + anio_fallecimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnio_nacimiento() {
        return anio_nacimiento;
    }

    public void setAnio_nacimiento(Integer anio_nacimiento) {
        this.anio_nacimiento = anio_nacimiento;
    }

    public Integer getAnio_fallecimiento() {
        return anio_fallecimiento;
    }

    public void setAnio_fallecimiento(Integer anio_fallecimiento) {
        this.anio_fallecimiento = anio_fallecimiento;
    }

    public void setAnio_nacimiento(int anio_nacimiento) {
        this.anio_nacimiento = anio_nacimiento;
    }



    public void setAnio_fallecimiento(int anio_fallecimiento) {
        this.anio_fallecimiento = anio_fallecimiento;
    }
}
