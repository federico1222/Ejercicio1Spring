package com.ejercicio_1.repositorios;

import com.ejercicio_1.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro,String> {
    
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE :titulo")
    public Libro buscarLibroPorNombre (@Param ("titulo") String titulo);
    
}
