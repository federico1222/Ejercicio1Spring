package com.ejercicio_1.servicios;


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
    public void guardarLibro(Libro libro) throws ErroresDeServicios {
        
        validar(libro);
        
        libroRepositorio.save(libro);
    }
    
    @Transactional(rollbackOn = Exception.class)
    public void modificarLibro( String id) throws ErroresDeServicios {

        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libroRepositorio.save(libro);
        } else {
            throw new ErroresDeServicios("No se encontro ningun libro con este id");
        }
    }

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
    public Libro buscarPorId(String id) throws ErroresDeServicios{
        Optional<Libro> option = libroRepositorio.findById(id);
        if (option.isPresent()) {
            Libro libro = option.get();
            return libro;
        }else{
            throw new ErroresDeServicios("Libro no encontrado");
        }
    }
    
    @Transactional(rollbackOn = Exception.class) 
    public List<Libro> listarLibro (){
        
        return libroRepositorio.findAll();       
    }

    //validaciones
    public void validar(Libro libro) throws ErroresDeServicios {

        if (libro.getIsbn()<1) {
            throw new ErroresDeServicios("ISBN incorrecto, Ingrese uno nuevamnete");
        }
        if (libro.getTitulo()== null || libro.getTitulo().isEmpty()) {
            throw new ErroresDeServicios("El titulo no puede estar vacio");
        }
        if (libroRepositorio.buscarLibroPorNombre(libro.getTitulo()) != null) {
            throw new ErroresDeServicios("ya existe un libro con este titulo");
        }
        if (libro.getAnio() == null) {
            throw new ErroresDeServicios("Ingrese un año correspondiente");
        }
        if (libro.getEjemplares() == null) {
            throw new ErroresDeServicios("Ingrese un numero de ejemplares");
        }
        if (libro.getEjemplaresPrestados()>libro.getEjemplares()) {
            throw new ErroresDeServicios("El numero de ejemplares prestados no puede ser mayor que el numero de ejemplares");
        }
        if (libro.getEjemplaresRestantes()> libro.getEjemplaresPrestados()) {
            throw new ErroresDeServicios("El numero de ejemplares restantes no puede ser mayor que el numero de ejemplares prestados");
        }
    }

}
