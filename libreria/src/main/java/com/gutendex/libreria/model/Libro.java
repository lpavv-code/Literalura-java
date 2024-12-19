package com.gutendex.libreria.model;

import com.gutendex.libreria.record.DatosLibro;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_libro")
  private Long id;

  @Column(unique = true)
  private String titulo;

  private Idioma idioma;

  @ManyToOne
  @JoinColumn(name = "id_autor")
  private Autor autor;

  private Integer numerDeDescargas;

  public Libro(DatosLibro datosLibro){
    this.titulo = datosLibro.titulo();
    Optional<String> idiomas = datosLibro.idioma().stream()
            .findFirst();
    idiomas.ifPresent(i ->
            this.idioma = Idioma.stringEnum(i));
    this.autor = new Autor(datosLibro.autor().get(0));
    this.numerDeDescargas = datosLibro.numeroDeDescargas();
  }

  @Override
  public String toString(){
    return """
           ***************************************
           **                Libro              **
           ***************************************
           > Título: %s
           > Autor: %s
           > Idioma: %s
           > Número De Descargas: %s
           """.formatted(titulo, autor.getNombre(), idioma.getIdioma(), numerDeDescargas);
  }

  public String descargas(){
    return """
           > Título: %s
           > Número De Descargas: %s
           """.formatted(titulo, numerDeDescargas);
  }
}
