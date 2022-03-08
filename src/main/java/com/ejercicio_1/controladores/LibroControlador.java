package com.ejercicio_1.controladores;

import com.ejercicio_1.entidades.Autor;
import com.ejercicio_1.entidades.Editorial;
import com.ejercicio_1.entidades.Libro;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.repositorios.AutorRepositorio;
import com.ejercicio_1.repositorios.EditorialRepositorio;
import com.ejercicio_1.servicios.LibroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    private LibroService libroServicio;
    private AutorRepositorio autorRepositorio;
    private EditorialRepositorio editorialRespositorio;

    @Autowired
    public LibroControlador(LibroService libroServicio,
            AutorRepositorio autorRepositorio,
            EditorialRepositorio editorialRespositorio) {
        this.libroServicio = libroServicio;
        this.autorRepositorio = autorRepositorio;
        this.editorialRespositorio = editorialRespositorio;
    }

    
    //registrar
    @GetMapping("/nuevoLibro")
    public String nuevoLibro(ModelMap model) {

        model.addAttribute("Titulo", "Nuevo Libro");
        model.addAttribute("descripcion", "Cargue aqui los datos del Libro");

        //Autor
        List<Autor> autores = autorRepositorio.findAll();
        model.addAttribute("autores", autores);
        //Editorial
        List<Editorial> editoriales = editorialRespositorio.findAll();
        model.addAttribute("editoriales", editoriales);

        return "libro/nuevoLibro.html";
    }

    
    @PostMapping("/registrarLibro")
    public String registrarLibro(
            @RequestParam (required = false) long isbn,
            @RequestParam (required = false) String titulo,
            @RequestParam (required = false) Integer anio,
            @RequestParam (required = false) Integer ejemplares,
            @RequestParam (required = false) Integer ejemplaresPrestados,
            @RequestParam (required = false) Integer ejemplaresRestantes,
            @RequestParam (required = false) String idAutor,
            @RequestParam (required = false) String idEditorial,
            RedirectAttributes attr) {
        try {
            
            libroServicio.guardarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial);
        } catch (ErroresDeServicios ex) {
            attr.addFlashAttribute("error", ex.getMessage());
            attr.addFlashAttribute("isbn", isbn);
            attr.addFlashAttribute("titulo", titulo);
            attr.addFlashAttribute("anio", anio);
            attr.addFlashAttribute("ejemplares", ejemplares);
            attr.addFlashAttribute("ejemplaresPrestados", ejemplaresPrestados);
            attr.addFlashAttribute("ejemplaresRestantes", ejemplaresRestantes);
            attr.addFlashAttribute("idAutor", idAutor);
            attr.addFlashAttribute("idEditorial", idEditorial);
            return "redirect:/libro/nuevoLibro";
        }

        return "libro/nuevoLibroCargado";
    }

    //modificar
    @GetMapping("/editar/{id}")
    public String actualizarLibro(@PathVariable String id, ModelMap model) throws ErroresDeServicios {

        Libro libro = libroServicio.buscarPorId(id);

        model.addAttribute("Titulo", "Actualizar Libro");
        model.addAttribute("descripcion", "Cargue aqui los datos nuevos del Libro");
        model.addAttribute("libro", libro);

        //Autor
        List<Autor> autores = autorRepositorio.findAll();
        model.addAttribute("autores", autores);
        //Editorial
        List<Editorial> editoriales = editorialRespositorio.findAll();
        model.addAttribute("editoriales", editoriales);

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
