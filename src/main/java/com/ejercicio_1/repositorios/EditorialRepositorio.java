package com.ejercicio_1.repositorios;

import com.ejercicio_1.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT e FROM Editorial e WHERE e.nombre LIKE :nombre")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);
}
