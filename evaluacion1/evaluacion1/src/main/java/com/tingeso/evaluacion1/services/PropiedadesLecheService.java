package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.PropiedadesLecheEntity;
import com.tingeso.evaluacion1.repositories.PropiedadesLecheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class PropiedadesLecheService {

    @Autowired
    PropiedadesLecheRepository propiedadesLecheRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioLecheService.class);

    public ArrayList<PropiedadesLecheEntity> obtenerPropiedadesLeche(){
        return (ArrayList<PropiedadesLecheEntity>) propiedadesLecheRepository.findAll();
    }

    public String guardarArchivoPropiedades(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            } else {
                return "Archivo guardado con exito";
            }
            return "Archivo guardado con exito";
        } else {
            return "No se pudo guardar el archivo";
        }
    }

    public void leerCSVPropiedades(String direccion) {
        String texto = "";
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    guardarPropiedadesLecheBD(bfRead.split(";")[0], Integer.valueOf(bfRead.split(";")[1]), Integer.valueOf(bfRead.split(";")[2]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente.");
        } catch (Exception e) {
            System.out.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarPropiedades(PropiedadesLecheEntity propiedades){
        propiedadesLecheRepository.save(propiedades);
    }

    public void guardarPropiedadesLecheBD(String codigo_proveedor, Integer porcentaje_solidos, Integer porcentaje_grasa) {
        PropiedadesLecheEntity nuevasPropiedades = new PropiedadesLecheEntity();
        nuevasPropiedades.setCodigo_proveedor(codigo_proveedor);
        nuevasPropiedades.setPorcentaje_solidos(porcentaje_solidos);
        nuevasPropiedades.setPorcentaje_grasa(porcentaje_grasa);
        guardarPropiedades(nuevasPropiedades);
    }
}
