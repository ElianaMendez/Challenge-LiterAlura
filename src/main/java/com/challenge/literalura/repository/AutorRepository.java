package com.challenge.literalura.repository;

import com.challenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Este metodo buscará en tu base de datos si ya existe un autor, con ese nombre, ignorando mayúsculas y minúsculas.
    Optional<Autor> findByNombreIgnoreCase(String nombre);
}