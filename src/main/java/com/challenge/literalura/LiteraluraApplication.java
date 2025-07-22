package com.challenge.literalura;

import com.challenge.literalura.principal.Principal;
import com.challenge.literalura.repository.AutorRepository;
import com.challenge.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BookRepository libroRepositorio;

	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {SpringApplication.run(LiteraluraApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepositorio, autorRepository);
		principal.muestraOpcionesMenu();
	}
}
