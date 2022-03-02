package com.ejercicio_1.servicios;

import com.ejercicio_1.entidades.Autor;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.repositorios.AutorRepositorio;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public Autor consultarAutor(String id) throws ErroresDeServicios {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            return autor;
        } else {
            throw new ErroresDeServicios("No se encontro nigun autor con ese id");
        }
    }

    @Transactional (rollbackOn= {Exception.class})
    public void guardarAutor(Autor autor) throws ErroresDeServicios {
        
       validar(autor);
       
       autorRepositorio.save(autor);
    }
    
    @Transactional
    public void altaAutor(String id) throws ErroresDeServicios {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(true);

            autorRepositorio.save(autor);
         
        } else {
            throw new ErroresDeServicios("No se encontro nigun autor con ese id");
        }
    }

    @Transactional
    public void bajaAutor(String id) throws ErroresDeServicios {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        } else {
            throw new ErroresDeServicios("No se encontro nigun autor con ese id");
        }
    }

    @Transactional
    public void eliminarAutor(String id) throws ErroresDeServicios {

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autorRepositorio.delete(autor);
        } else {
            throw new ErroresDeServicios("No se encontro nigun autor con ese id");
        }
    }
    
    @Transactional 
    public List<Autor> listaAutores (){
        
        return autorRepositorio.findAll();
    }
    
    @Transactional(rollbackOn = Exception.class)
    public Autor buscarPorId(String id) throws ErroresDeServicios{
        Optional<Autor> option = autorRepositorio.findById(id);
        if (option.isPresent()) {
            Autor autor = option.get();
            return autor;
        }else{
            throw new ErroresDeServicios("Autor no encontrado");
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public Autor buscarPorNombre(String nombre) throws ErroresDeServicios{
     
        return autorRepositorio.buscarAutorPorNombre(nombre);
    }
    public void validar(Autor autor) throws ErroresDeServicios{
        
        if (autor.getNombre().isEmpty()) {
            throw new ErroresDeServicios("Autor vacio, ingresa un autor nuevamente");
        }
    }
    
    
}
