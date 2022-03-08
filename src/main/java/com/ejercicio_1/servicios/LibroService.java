package com.ejercicio_1.servicios;

import com.ejercicio_1.entidades.Autor;
import com.ejercicio_1.entidades.Editorial;
import com.ejercicio_1.entidades.Libro;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorService AutorService;
    @Autowired
    private EditorialService EditorialService;

    public Libro consultarLibro(String id) throws ErroresDeServicios {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErroresDeServicios("No se encontro un libro con ese id");
        }
    }

    @Transactional(rollbackOn = Exception.class) //Si el metodo se ejecuta sin lanzar excepciones se hace un comit a la base de datos y se aplican todos los cambios
    public void guardarLibro(long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ErroresDeServicios {
        
        Autor autor = AutorService.buscarPorId(idAutor);
        Editorial editorial = EditorialService.buscarPorId(idEditorial);

        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

//    @Transactional(rollbackOn = Exception.class)
//    public void modificarLibro( String id) throws ErroresDeServicios {
//
//        Optional<Libro> respuesta = libroRepositorio.findById(id);
//
//        if (respuesta.isPresent()) {
//            Libro libro = respuesta.get();
//            libroRepositorio.save(libro);
//        } else {
//            throw new ErroresDeServicios("No se encontro ningun libro con este id");
//        }
//    }
    @Transactional(rollbackOn = Exception.class)
    public void altaLibro(String id) throws ErroresDeServicios {

        //validamos que el libro exista
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new ErroresDeServicios("No se encontro ningun libro con este id");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void bajaLibro(String id) throws ErroresDeServicios {

        //validamos que el libro exista
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErroresDeServicios("No se encontro ningun libro con este id");
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public void eliminarlibro(String id) throws ErroresDeServicios {
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            libroRepositorio.delete(libro);
        } else {
            throw new ErroresDeServicios("No se encontro ningun libro con este id");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Libro buscarPorId(String id) throws ErroresDeServicios {
        Optional<Libro> option = libroRepositorio.findById(id);
        if (option.isPresent()) {
            Libro libro = option.get();
            return libro;
        } else {
            throw new ErroresDeServicios("Libro no encontrado");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public List<Libro> listarLibro() {

        return libroRepositorio.findAll();
    }

    //validaciones
    public void validar(long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, Autor autor, Editorial editorial) throws ErroresDeServicios {

        if (isbn < 1) {
            throw new ErroresDeServicios("ISBN incorrecto, Ingrese uno nuevamnete");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErroresDeServicios("El titulo no puede estar vacio");
        }
        if (libroRepositorio.buscarLibroPorNombre(titulo) != null) {
            throw new ErroresDeServicios("ya existe un libro con este titulo");
        }
        if (anio == null || anio.equals("")) {
            throw new ErroresDeServicios("Ingrese un año correspondiente");
        }
        if (ejemplares == null || ejemplares.equals("")) {
            throw new ErroresDeServicios("Ingrese el numero de ejemplares");
        }
        if (ejemplaresPrestados== null || ejemplaresPrestados.equals("")) {
            throw new ErroresDeServicios("Ingrese el numero de ejemplares prestados");
        }
        if (ejemplaresRestantes==null || ejemplaresRestantes.equals("")) {
            throw new ErroresDeServicios("Ingrese el numero de ejemplares restantes");
        }
        if (autor == null) {
            throw new ErroresDeServicios(("autor ingresado null"));
        }
        if (editorial == null) {
            throw new ErroresDeServicios(("editorial ingresado null"));
        }
    }

}
