package com.ejercicio_1.controladores;

import com.ejercicio_1.entidades.Editorial;
import com.ejercicio_1.errores.ErroresDeServicios;
import com.ejercicio_1.servicios.EditorialService;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    private  EditorialService editorialServicio;
    
    @Autowired
    public EditorialControlador (EditorialService editorialServicio){
        this.editorialServicio=editorialServicio;
    }
    
    //registro
    @GetMapping("/nuevaEditorial")
    public String nuevaEditorial(ModelMap model) {
        
        model.addAttribute("titulo", "Nueva Editorial");
        model.addAttribute("descripcion", "Cargue aqui los datos de la Editorial");
        model.addAttribute("editorial", new Editorial());        
        
        return "editorial/nuevaEditorial";
    }
    
    @PostMapping("/registrarEditorial")
    public String registrarEditorial(@ModelAttribute Editorial editorial,
            RedirectAttributes attr)  {
        
        try {
            editorialServicio.guardarEditorial(editorial);
        } catch (ErroresDeServicios ex) {
            attr.addFlashAttribute("error", ex.getMessage());
            attr.addFlashAttribute("editorial", editorial);           
            return "redirect:/editorial/nuevaEditorial";
        }
        
        return "editorial/nuevaEditorialCargada";
    }
   
    //modificar
    @GetMapping("/editar/{id}")
    public String actualizarEditorial(@PathVariable String id, ModelMap model) throws ErroresDeServicios {
        
        Editorial editorial = editorialServicio.buscarPorId(id);
        model.addAttribute("titulo", "Actualizar Editorial");
        model.addAttribute("descripcion", "Cargue aqui los datos nuevos de la Editorial");
        model.addAttribute("editorial", editorial);        
        
        
        return "editorial/nuevaEditorial";
    }
    //eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminar (@PathVariable String id) throws ErroresDeServicios{
        
        editorialServicio.eliminarEditorial(id);
        
        return "redirect:/editorial/ListaEditorial";
    }

    
    //listar
    @GetMapping("/ListaEditorial")
    public String listaEditorial(ModelMap model) {
        
        List<Editorial> editoriales= editorialServicio.listaEditoriales();
        
        model.addAttribute("editoriales", editoriales);
        
        return "editorial/ListaEditorial";
    }
}
    

