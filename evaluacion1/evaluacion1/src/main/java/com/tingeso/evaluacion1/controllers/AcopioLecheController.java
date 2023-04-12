package com.tingeso.evaluacion1.controllers;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.services.AcopioLecheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class AcopioLecheController {

    @Autowired
    AcopioLecheService acopioLecheService;

    @GetMapping("/subirArchivoAcopio")
    public String subirArchivoAcopios(){
        return "subirArchivoAcopio";
    }

    @PostMapping("/subirArchivoAcopio")
    public String subirAcopio(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        acopioLecheService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo cargado!");
        acopioLecheService.leerCSV("Acopio.csv");
        return "redirect:/subirArchivoAcopio";
    }

    @GetMapping("/informacionAcopios")
    public String listasAcopios(Model model){
        ArrayList<AcopioLecheEntity> acopios= acopioLecheService.obtenerAcopiosLeche();
        model.addAttribute("acopios", acopios);
        return "informacionAcopios";
    }
}
