
package com.ejercicio_1.repositorios;

import com.ejercicio_1.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository <Autor,String>{
    
    @Query("SELECT a FROM Autor a WHERE a.nombre LIKE :nombre")
    public Autor buscarAutorPorNombre (@Param ("nombre") String nombre);
}
