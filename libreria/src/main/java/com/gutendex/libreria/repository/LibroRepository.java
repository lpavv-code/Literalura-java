package com.gutendex.libreria.repository;


import com.gutendex.libreria.model.Idioma;
import com.gutendex.libreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

  List<Libro> findTop10ByOrderByNumerDeDescargasDesc();

  @Query("SELECT i FROM Libro i where i.idioma=:idioma")
  List<Libro> findLibroByIdioma(Idioma idioma);
}
