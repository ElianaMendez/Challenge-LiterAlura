package com.challenge.literalura.repository;

import com.challenge.literalura.model.Autor;
import com.challenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Este metodo buscará en tu base de datos si ya existe un autor, con ese nombre, ignorando mayúsculas y minúsculas.
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    // Fetches authors alive in a specific year from the DB
    @Query("SELECT a FROM Autor a WHERE a.anio_nacimiento <= :anioAutoresVivos AND (a.anio_fallecimiento IS NULL OR a.anio_fallecimiento >= :anioAutoresVivos)")
    List<Autor> findAutoresVivosEnAnio(Integer anioAutoresVivos);
}