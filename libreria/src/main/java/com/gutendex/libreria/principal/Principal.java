package com.gutendex.libreria.principal;

import java.util.*;

import com.gutendex.libreria.model.Autor;
import com.gutendex.libreria.model.Idioma;
import com.gutendex.libreria.model.Libro;
import com.gutendex.libreria.record.DatosResult;
import com.gutendex.libreria.repository.AutorRepository;
import com.gutendex.libreria.repository.LibroRepository;
import com.gutendex.libreria.service.ConsumoAPI;
import com.gutendex.libreria.service.ConvertirDatos;
import org.springframework.dao.DataIntegrityViolationException;

public class Principal {

  private Scanner sc = new Scanner(System.in);
  private ConsumoAPI consumoApi = new ConsumoAPI();
  private ConvertirDatos convierteDatos = new ConvertirDatos();
  private LibroRepository libroRepositorio;
  private AutorRepository autorRepositorio;
  private Optional<Libro> libroBuscado;

  public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
    this.libroRepositorio = libroRepository;
    this.autorRepositorio = autorRepository;
  }

  private final String MENU_OPCIONES = """
          **********************************************
          ***              Literalura                ***
          **********************************************
          [1] Buscar Libro por título
          [2] Lista de Libros Registrados (BD)
          [3] Lista de Autores Registrados (BD)
          [4] Lista de Autores Vivos en Determinado Año
          [5] Lista de Libros por Idiomas
          [6] TOP 10 Libros Descargados
          ...............................................
          [0] Salir
          """;

  public void muestraMenu(){
    var opc = -1;
    while(opc!=0){
      System.out.println(MENU_OPCIONES);
      System.out.print("Digite una opción: ");
      try{
        opc = Integer.parseInt(sc.nextLine());
        switch (opc){
          case 1:
            buscarLibro();
            break;
          case 2:
            System.out.println("""
                    ******* LIBROS REGISTRADOS ********\n
                    """);
            listaDelibrosRegistrados();
            break;
          case 3:
            System.out.println("""
                    ******* AUTORES REGISTRADOS ********\n
                    """);
            listaDeAutoresRegistrados();
            break;
          case 4:
            autoreVivosEntreFechas();
            break;
          case 5:
            listaPorIdioma();
            break;
          case 6:
            top10Libros();
            break;
          default:
            System.out.println("Opción Incorrecta. Elija nuevamente");
        }
      }catch (NumberFormatException e){
        System.out.println("Debes selecciona una opción entre [0 - 7]");
      }

    }
  }

  private DatosResult getDatosResult(){
    System.out.print("Escribe el nombre del libro que deseas buscar: ");
    var tituloBuscado = sc.nextLine();
    String url = "https://gutendex.com/books/?search="+tituloBuscado.replace(" ","%20");
    var json = consumoApi.obtenerDatos(url);
    DatosResult datosBuscados = convierteDatos.obtenerDatos(json, DatosResult.class);
    return datosBuscados;
  }

  private void buscarLibro(){
    Optional<Libro> libros = getDatosResult().resultLibro().stream()
            .findFirst()
            .map(l -> new Libro(l));

    if(libros.isPresent()){
      Libro libro = libros.get();

      if(libro.getAutor() != null){
        Autor autor = autorRepositorio.findAutorByNombre(libro.getAutor().getNombre());

        if(autor == null){
          Autor nuevoAutor = libro.getAutor();
          autor = autorRepositorio.save(nuevoAutor);
        }

        try{
          libro.setAutor(autor);
          libroRepositorio.save(libro);
          System.out.println(libro);
        }catch (DataIntegrityViolationException e){
          System.out.println("El libro que has buscado ya se encuentra registrado en la BD. ");
        }
      }
    }else{
      System.out.println("No se encuntra el libro en Gutendex");
    }
  }

  private void listaDelibrosRegistrados(){
    List<Libro> libros = libroRepositorio.findAll();
    libros.forEach(System.out::println);
  }

  private void listaDeAutoresRegistrados(){
    List<Autor> autores = autorRepositorio.findAll();
    autores.forEach(System.out::println);
  }

  private void autoreVivosEntreFechas(){
    System.out.print("Ingrese el Año de vida de autor que deseas encontrar: ");
    try{
      int year = sc.nextInt();
      List<Autor> autores = autorRepositorio.findAutorBetweenYear(year);
      if(autores.isEmpty()){
        System.out.println("No se encontro registro de autores vivos durante ese año");
      } else {
        autores.forEach(System.out::println);
      }
    } catch (InputMismatchException e) {
      System.out.println("Ingresa un Año Valido. ");
    }
    sc.nextLine();
  }

  private void listaPorIdioma(){
    Idioma.listarIdiomas();
    System.out.print("""
            ****     LISTA DE IDIOMAS     ****
            ----------------------------------
            CODIGO
            > es <  Español
            > en <  Inglés
            > fr <  Francés
            > pt <  Portugés
            > zh <  Chino
            ................
            > 0  <  SALIR
            ----------------------------------
            """);
    System.out.println("Ingrese el codigo del Idioma");
    String idioma = sc.nextLine();
    if(idioma.equals("0")){
      return;
    }
    List<Libro> libro = libroRepositorio.findLibroByIdioma(Idioma.stringEnum(idioma));
    libro.forEach(System.out::println);
    sc.nextLine();
  }

  private void top10Libros(){
    List<Libro> topLibros = libroRepositorio.findTop10ByOrderByNumerDeDescargasDesc();
    System.out.println("""
            **********************************************
            **          TOP 10 LIBROS DESCARGADOS       **
            **********************************************
            """);
    topLibros.forEach(l ->
            System.out.println(l.descargas()));
  }

}
