package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.repositories.AcopioLecheRepository;
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
import java.util.Objects;

@Service
public class AcopioLecheService {

    @Autowired
    AcopioLecheRepository acopioLecheRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioLecheService.class);

    public ArrayList<AcopioLecheEntity> obtenerAcopiosLeche() {
        return (ArrayList<AcopioLecheEntity>) acopioLecheRepository.findAll();
    }

    public String guardar(MultipartFile file) {
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

    public void leerCSV(String direccion) {
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
                    guardarAcopioBD(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], Integer.valueOf(bfRead.split(";")[3]));
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

    public AcopioLecheEntity guardarAcopio(AcopioLecheEntity acopio) {
        return acopioLecheRepository.save(acopio);
    }

    public void guardarAcopioBD(String fecha, String turno, String codigo_proveedor, Integer kilos_leche) {
        AcopioLecheEntity nuevoAcopio = new AcopioLecheEntity();
        nuevoAcopio.setFecha(fecha);
        nuevoAcopio.setTurno(turno);
        nuevoAcopio.setCodigo_proveedor(codigo_proveedor);
        nuevoAcopio.setKilos_leche(kilos_leche);
        guardarAcopio(nuevoAcopio);
    }

    public void eliminarAcopios() {
        acopioLecheRepository.deleteAll();
    }


    /*
    Descripcion metodo: Metodo que obtiene todos los acopios pertenecientes a un Proveedor.
    Parametros de entrada: codigo del proveedor(String).
    Retorno: Lista con los acopios pertenecientes a ese Proveedor(ArrayList<AcopioLecheEntity>).
    */
    public ArrayList<AcopioLecheEntity> obtenerAcopiosProveedor(String codigo_proveedor) {
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios = (ArrayList<AcopioLecheEntity>) acopioLecheRepository.findAll();
        ArrayList<AcopioLecheEntity> acopiosProveedor = new ArrayList<>();
        for (int i = 0; i < acopios.size(); ++i) {
            if (Objects.equals(acopios.get(i).getCodigo_proveedor(), codigo_proveedor)) {
                acopiosProveedor.add(acopios.get(i));
            }
        }
        return acopiosProveedor;
    }
}
