package com.gutendex.libreria.repository;

import com.gutendex.libreria.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

  Autor findAutorByNombre(String nombre);

  @Query(value = "SELECT * FROM autores WHERE :year >= year_nacimiento AND :year < year_fallecimiento",nativeQuery = true)
  List<Autor> findAutorBetweenYear(int year);
//
}

