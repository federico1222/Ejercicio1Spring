package com.ejercicio_1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrincipalControlador {
    
   //index
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    //opciones
    @GetMapping("/opciones")
    public String opciones() {
        return "opciones.html";
    }   
    //libro
    @GetMapping("/libro")
    public String libro() {
        return "libro.html";
    }   
    //autor
    @GetMapping("/autor")
    public String autor() {
        return "autor.html";
    }      
    //editorial
    @GetMapping("/editorial")
    public String editorial() {
        return "editorial.html";
    }
    
}
   