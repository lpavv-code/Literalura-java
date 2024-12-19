package com.gutendex.libreria.model;

import com.gutendex.libreria.record.DatosAutor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "id_autor")
  private Long id;

  @Column (unique = true)
  private String nombre;

  private Integer yearNacimiento;
  private Integer yearFallecimiento;

  @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Libro> libro;

  public Autor(DatosAutor datosAutor){
    this.nombre= datosAutor.nombre();
    this.yearNacimiento = datosAutor.fechaDeNacimiento();
    this.yearFallecimiento = datosAutor.fechaDeFallecimiento();
  }

  @Override
  public String toString(){
    return """
           ***********************************
           **              Autor            **
           ***********************************
           > Nombre : %s
           > Fecha de Nacimiento: %s
           > Fecha de Fallecimiento: %s
           > Libros : %s
           ...................................
           """.formatted(nombre, yearNacimiento, yearFallecimiento, libro.stream()
            .map(l -> l.getTitulo()).collect(Collectors.toList()));

  }
}
