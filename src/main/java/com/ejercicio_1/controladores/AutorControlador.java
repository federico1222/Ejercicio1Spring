package com.ejercicio_1.controladores;

import com.ejercicio_1.entidades.Autor;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.servicios.AutorService;
import java.util.List;
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
@RequestMapping("/autor")
public class AutorControlador {

    private AutorService autorServicio;

    @Autowired
    public AutorControlador(AutorService autorServicio) {
        this.autorServicio = autorServicio;
    }

    //registro
    @GetMapping("/nuevoAutor")//url de del proyecto
    public String nuevoAutor(ModelMap model) {
        model.addAttribute("titulo", "Nuevo Autor");
        model.addAttribute("descripcion", "Cargue aqui los datos del Autor");
        model.addAttribute("autor", new Autor());

        return "autor/nuevoAutor";//ruta donde esta guardado el template
    }

    @PostMapping("/registrarAutor")
    public String registrarAutor(@ModelAttribute Autor autor, ModelMap model, 
            RedirectAttributes attr)   {

        try {
            autorServicio.guardarAutor(autor);
        } catch (ErroresDeServicios ex) {
           attr.addFlashAttribute("error", ex.getMessage());
           attr.addFlashAttribute("autor", autor);
           
           return "redirect:/autor/nuevoAutor";
        }

        return "autor/autorAgregado";
    }

    //editar en lista 
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, ModelMap model) throws ErroresDeServicios {

        Autor autor = autorServicio.buscarPorId(id);
        
        model.addAttribute("titulo", "Actualizar Autor");
        model.addAttribute("descripcion", "Cargue aqui los datos nuevos del Autor");
        model.addAttribute("autor", autor);

        return "autor/nuevoAutor";
    }
    
    //eliminar en lista
    @GetMapping("/eliminar/{id}")
    public String eliminar (@PathVariable String id) throws ErroresDeServicios{
        
        autorServicio.eliminarAutor(id);
        
        return "redirect:/autor/listaAutores";
    }
    
    //actualizar por nombre
    @GetMapping("/actualizarAutor")
    public String Actualizar (ModelMap model) {
        
        model.addAttribute("autor", new Autor());
        
        return "autor/actualizarAutor";
    }
    
    @PostMapping("/actualizarAutor")
    public String actualizarAutor (@ModelAttribute Autor autor) throws ErroresDeServicios {

        autorServicio.guardarAutor(autor);

        return "autor/actualizarAutor";
    }
    
    @GetMapping("/actualizarAutorPorNombre/{nombre}")
    public String actualizarAutorPorNombre (@PathVariable String nombre ,ModelMap model) throws ErroresDeServicios {
        
        Autor autor = autorServicio.buscarPorNombre(nombre);
        
        model.addAttribute("titulo", "Actualizar Autor");
        model.addAttribute("descripcion", "Cargue aqui los datos nuevos del Autor");
        model.addAttribute("autor", autor);
        
        return "autor/actualizarAutorPorNombre";
    }
    
    
    
    
    //listar 
    @GetMapping("/listaAutores")
    public String listaAutores(ModelMap model) {

        List<Autor> autores = autorServicio.listaAutores();

        model.addAttribute("autores", autores);

        return "autor/listaAutores.html";
    }

}
