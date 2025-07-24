package com.challenge.literalura.principal;
import com.challenge.literalura.model.*;
import com.challenge.literalura.repository.AutorRepository;
import com.challenge.literalura.repository.BookRepository;
import com.challenge.literalura.service.ConsumoAPI;
import com.challenge.literalura.service.ConvierteDatos;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner datoTeclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private List<DatosAutor> datosAutores = new ArrayList<>();
    private BookRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(BookRepository bookRepository, AutorRepository autorRepository){
        this.libroRepositorio = bookRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraOpcionesMenu(){
        var opcion =-1;
        while(opcion != 0){
            var menu = """
                    Ingrese el número de la opción/acción que quiere realizar:
                    
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    
                    """;
            System.out.println(menu);
            opcion = datoTeclado.nextInt();
            datoTeclado.nextLine();

            switch (opcion){
                case 1:
                    BuscarLibroPorTitulo();
                    break;
                case 2:
                    ListarLibrosRegistrados();
                    break;
                case 3:
                    ListarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivosAnio();
                        break;
                case 5:
                    ListarLibrosPorIdioma();
                        break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
           }
        }
    }

    private Optional<DatosLibros> GetDatosLibros() {
        System.out.println("Escribe el nombre del libro que quieres buscar: ");
        var libroBuscado = datoTeclado.nextLine();

        //Prueba revisión URL utilizada para consumir API
        var urlParaBuscar = URL_BASE + "?search=" + libroBuscado.replace(" ", "+").toLowerCase();
        System.out.println("Requesting URL: " + urlParaBuscar);

        var json = consumoAPI.obtenerDatos(urlParaBuscar);

        //Convierte el JSON a la clase 'Datos' que contiene la lista de resultados.
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        //De la lista de resultados, busca el primer libro.
        Optional<DatosLibros> primerLibro = datos.resultados().stream()
                .findFirst();
        return primerLibro;

    }

    private void BuscarLibroPorTitulo() {
        // Llama al metodo que devuelve Optional de datos obtenidos para la busqueda realizada
        Optional<DatosLibros> datosLibroOptional = GetDatosLibros();

        // Comprueba si el Optional contiene un valor (si se encontró un libro)
        if (datosLibroOptional.isPresent()) {
            // Si existe, obtén el objeto DatosLibro
            DatosLibros datosLibro = datosLibroOptional.get();

            // Primero, verifica si el libro ya existe en la BD para no duplicarlo
            Optional<Libro> libroExistente = libroRepositorio.findByTituloContainsIgnoreCase(datosLibro.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("\nEl libro '" + libroExistente.get().getTitulo() + "' ya está registrado.\n");
                return; // Sale del metodo para no guardar el duplicado
            }

            // Verifica si el libro tiene autor antes de acceder
            if (datosLibro.autores() == null || datosLibro.autores().isEmpty()) {
                System.out.println("\nEl libro '" + datosLibro.titulo() + "' no tiene información de autor disponible.\n");
                return;
            }

            // Si el libro no existe y tiene autores, procesamos el autor
            DatosAutor datosAutor = datosLibro.autores().get(0); // Tomamos el primer autor
            Optional<Autor> autorExistente = autorRepositorio.findByNombreIgnoreCase(datosAutor.nombre());

            Libro libro;
            if (autorExistente.isPresent()) {
                // Si el autor ya existe en la BD, lo usamos
                Autor autor = autorExistente.get();

                //Creamos la instancia del libro y le asigno el autor asistente
                libro = new Libro(datosLibro);
                libro.setAutor(autor);

                //Se guarda el libro
                libroRepositorio.save(libro);

            } else {
                //Creamos nueva instancia de Autor y libro
                Autor nuevoAutor = new Autor(datosAutor);
                libro = new Libro(datosLibro);
                libro.setAutor(nuevoAutor);

                // Save autor first, then libro
                autorRepositorio.save(nuevoAutor);
                libroRepositorio.save(libro);
            }

            System.out.println("\n----- LIBRO GUARDADO CON ÉXITO -----");
            System.out.println(libro);
            System.out.println("-------------------------------------\n");

        } else {
            System.out.println("\nNo se encontró ningún libro con ese título.\n");
        }
    }

    private void ListarLibrosRegistrados() {
        List<Libro> libros = libroRepositorio.findAllWithAuthor();

        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados en la base de datos.\n");
            return;
        }

        System.out.println("\n========== LIBROS REGISTRADOS ==========");
        libros.forEach(libro -> {
            System.out.println("\nTítulo: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Idioma: " + (libro.getIdioma() != null ? libro.getIdioma() : "No disponible"));
            System.out.println("Descargas: " + (libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : "0"));
            System.out.println("-------------------------------");
        });
        System.out.println("=========================================\n");
    }

    private void ListarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados en la base de datos.\n");
            return;
        }

        System.out.println("\n========== AUTORES REGISTRADOS ==========");
        autores.forEach(autor -> {
            System.out.println("\nNombre: " + autor.getNombre());
            System.out.println("Año de Nacimiento: " + (autor.getAnio_nacimiento() != null ? autor.getAnio_nacimiento() : "No disponible"));
            System.out.println("Año de Fallecimiento: " + (autor.getAnio_fallecimiento() != null ? autor.getAnio_fallecimiento() : "No disponible"));

            String librosEscritos;
            if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
                // Use stream() to map each book to its title and join them into a single string
                librosEscritos = autor.getLibros().stream()
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining(", "));
            } else {
                librosEscritos = "No tiene libros registrados.";
            }

            System.out.println("Libros: " + librosEscritos);
            System.out.println("-------------------------------");
        });
        System.out.println("=========================================\n");
    }

    private void ListarAutoresVivosAnio(){
        System.out.println("Ingresa un año para el que quieras conocer autores registrados y vivos ese año");

        try{
            Integer anioAutoresVivos = Integer.parseInt(datoTeclado.nextLine());
            List<Autor> autoresVivos = autorRepositorio.findAutoresVivosEnAnio(anioAutoresVivos);

            if(autoresVivos.isEmpty()){
                System.out.println("\nNo se encontraron autores vivos registrados en el año " + anioAutoresVivos + ".");
            } else{
                System.out.println("\nAutores vivos en el año " + anioAutoresVivos + ":");
                autoresVivos.forEach(autor -> {
                    System.out.println("________________________________________");
                    System.out.println("Autor: " + autor.getNombre());
                    System.out.println("Fecha de Nacimiento: " + autor.getAnio_nacimiento());
                    System.out.println("Fecha de Fallecimiento: " + (autor.getAnio_fallecimiento() != null ? autor.getAnio_fallecimiento() : "Aún con vida!"));
                });
                System.out.println("________________________________________");
            }
        }catch (InputMismatchException e){
            System.out.println("\nError: Por favor ingresar un año válido (ej: 1950).");
            datoTeclado.nextLine(); //Limpia el año inválido ingresado previamente.
        }
    }

    private void ListarLibrosPorIdioma(){
        System.out.println("""
                Elige el idioma del cual quieres ver los libros
                ______________________
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                ______________________
                """);
        String idiomaElegido = datoTeclado.nextLine().toLowerCase().trim();

        String nombreDeIdiomaCompleto;
        switch (idiomaElegido){
            case "es":
                nombreDeIdiomaCompleto = "Español";
                break;
            case "en":
                nombreDeIdiomaCompleto = "Inglés";
                break;
            case "fr":
                nombreDeIdiomaCompleto = "Francés";
                break;
            case "pt":
                nombreDeIdiomaCompleto = "Portugués";
                break;
            default:
                System.out.println("Por favor eliga una opción válida");
                return;
        }

        List<Libro> libros = libroRepositorio.findByIdiomaWithAutor(idiomaElegido);

        if (libros.isEmpty()){
            System.out.println("\nNo se encontraron libros en " + nombreDeIdiomaCompleto);
            return;
        }
        System.out.println("\nLibros encontrados en " + nombreDeIdiomaCompleto + ":");
        libros.forEach(libro -> {
            System.out.println("\n-----------LIBRO------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Descargas: " + (libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : "0"));
        });
        System.out.println("\n----------------------------");
    }
}
