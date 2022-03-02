package com.ejercicio_1.servicios;

import com.ejercicio_1.entidades.Editorial;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.repositorios.EditorialRepositorio;
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    public Editorial consultarEditorial (String id) throws ErroresDeServicios {
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            return editorial;
        } else {
            throw new ErroresDeServicios("No se encontro niguna editorial con ese id");
        }
        
    }
    
    @Transactional
    public void guardarEditorial (Editorial editorial) throws ErroresDeServicios{
        
        if (editorial.getNombre().isEmpty()) {
            throw new ErroresDeServicios("Debe ingresar el nombre de la editorial");
        }
               
        editorialRepositorio.save(editorial);       
    }
    
    @Transactional
    public void modificarEditorial (String id, String nombreEditorial) throws ErroresDeServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombreEditorial);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErroresDeServicios("No se encontro niguna editorial con ese id");
        }
    }
    
    @Transactional
    public void altaEditorial (String id) throws ErroresDeServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErroresDeServicios("No se encontro niguna editorial con ese id");
        }
    }
    
    @Transactional
    public void bajaEditorial (String id) throws ErroresDeServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            
            editorialRepositorio.save(editorial);
        } else {
            throw new ErroresDeServicios("No se encontro niguna editorial con ese id");
        }
    }
    
    @Transactional
    public void eliminarEditorial (String id) throws ErroresDeServicios {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            
            editorialRepositorio.delete(editorial);
        } else {
            throw new ErroresDeServicios("No se encontro ninguna editorial con ese id");
        }
    }
    
   @Transactional 
    public List<Editorial> listaEditoriales (){
        
        return editorialRepositorio.findAll();
        
    }
    @Transactional(rollbackOn = Exception.class)
    public Editorial buscarPorId(String id) throws ErroresDeServicios{
        Optional<Editorial> option = editorialRepositorio.findById(id);
        if (option.isPresent()) {
            Editorial editorial = option.get();
            return editorial;
        }else{
            throw new ErroresDeServicios("Autor no encontrado");
        }
    }
}
