package com.gutendex.libreria;

import com.gutendex.libreria.repository.AutorRepository;
import com.gutendex.libreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gutendex.libreria.principal.Principal;

@SpringBootApplication
public class LibreriaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	@Autowired
	private AutorRepository autorRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraMenu();

	}

}
