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

//    @Query("SELECT a FROM autor a LEFT JOIN FETCH a.libros")
//    List<Autor> findAllWithBooks();
}