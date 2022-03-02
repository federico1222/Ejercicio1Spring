package com.ejercicio_1.controladores;

import com.ejercicio_1.entidades.Autor;
import com.ejercicio_1.entidades.Editorial;
import com.ejercicio_1.entidades.Libro;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.servicios.AutorService;
import com.ejercicio_1.servicios.EditorialService;
import com.ejercicio_1.servicios.LibroService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    private LibroService libroServicio;
    private AutorService autorServicio;
    private EditorialService editorialServicio;

    @Autowired
    public LibroControlador(LibroService libroServicio,
        AutorService autorService,
        EditorialService editorialService) {
        this.libroServicio = libroServicio;
        this.autorServicio = autorServicio;
        this.editorialServicio = editorialServicio;
    }

    //registrar
    @GetMapping("/nuevoLibro")
    public String nuevoLibro(ModelMap model) {

        model.addAttribute("titulo", "Nuevo Libro");
        model.addAttribute("descripcion", "Cargue aqui los datos del Libro");
        model.addAttribute("libro", new Libro());
        return "libro/nuevoLibro.html";
    }

    @PostMapping("/registrarLibro")
    public String registrarLibro(@ModelAttribute Libro libro,
            RedirectAttributes attr){

        try {
            libroServicio.guardarLibro(libro);      
        } catch (ErroresDeServicios ex) {
            attr.addFlashAttribute("error", ex.getMessage());
            attr.addFlashAttribute("libro", libro);
            return "redirect:/libro/nuevoLibro";
        }
        
        return "libro/nuevoLibroCargado";
    }

    //modificar
    @GetMapping("/editar/{id}")
    public String actualizarLibro(@PathVariable String id, ModelMap model) throws ErroresDeServicios {

        Libro libro = libroServicio.buscarPorId(id);

        model.addAttribute("titulo", "Actualizar Libro");
        model.addAttribute("descripcion", "Cargue aqui los datos nuevos del Libro");
        model.addAttribute("libro", libro);

        return "libro/nuevoLibro.html";
    }

    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws ErroresDeServicios {

        libroServicio.eliminarlibro(id);

        return "redirect:/libro/listarLibro";
    }

    //listar
    @GetMapping("/listarLibro")
    public String listarLibro(ModelMap model) {

        List<Libro> libros = libroServicio.listarLibro();

        model.addAttribute("libros", libros);

        return "libro/listarLibro.html";
    }

}
