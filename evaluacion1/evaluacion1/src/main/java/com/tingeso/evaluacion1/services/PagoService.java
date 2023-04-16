package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.entities.PagoEntity;
import com.tingeso.evaluacion1.entities.PropiedadesLecheEntity;
import com.tingeso.evaluacion1.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    AcopioLecheService acopioLecheService;

    @Autowired
    PropiedadesLecheService propiedadesLecheService;

    public PagoEntity calculosPago(String codigo_proveedor) {
        PagoEntity nuevoPago = new PagoEntity();
        //Se obtienen los acopios de la quincena del proveedor.
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios = acopioLecheService.obtenerAcopiosProveedor(codigo_proveedor);
        //Se obtienen las propiedades de la leche de la quincena del proveedor.
        PropiedadesLecheEntity propiedades_proveedor=new PropiedadesLecheEntity();
        propiedades_proveedor=propiedadesLecheService.obtenerPropiedadesProveedor(codigo_proveedor);
        nuevoPago.setTotal_klsleche(totalKlsLeche(acopios));
        return nuevoPago;
    }

    /*
    Descripcion metodo: Metodo que obtiene el total de kls de leche de la quincena de un proveedor.
    Parametros de entrada: Acopios del proveedor(ArrayList<AcopioLecheEntity>).
    Retorno: Total de kilos de leche de la quincena(int).
    */
    public int totalKlsLeche(ArrayList<AcopioLecheEntity> acopios_proveedor) {
        int totalKls = 0;
        for (int i = 1; i <= acopios_proveedor.size(); ++i) {
            totalKls = totalKls + acopios_proveedor.get(i).getKilos_leche();
        }
        return totalKls;
    }

    /*
    Descripcion metodo: Metodo que obtiene la cantidad de dias dentro de la quincena que un proveedor envio leche.
    Parametros de entrada: Acopios(ArrayList<AcopioLecheEntity>).
    Retorno: Cantidad de dias en los que se realizaron acopios(int).
    */
    public int cantidadDiasEnvio(ArrayList<AcopioLecheEntity> acopios_proveedor) {
        int diaActual;
        ArrayList<Integer> dias=new ArrayList<>();
        for (AcopioLecheEntity acopioLecheEntity : acopios_proveedor) {
            diaActual = obtenerDiaFecha(acopioLecheEntity.getFecha());
            if (!dias.contains(diaActual)) {
                dias.add(diaActual);
            }
        }
        return dias.size();
    }

    /*
    Descripcion metodo: Metodo que obtiene el promedio diario de kls de leche enviados por un proveedor.
    Parametros de entrada:
    Retorno:
    */
    public Integer promedioDiarioLeche() {
        Integer promedio = 0;
        return promedio;
    }

    /*
    Descripcion metodo: Metodo que obtiene el dia de una fecha de tipo String.
    Parametros de entrada: fecha con formato yyyy/mm/dd(String).
    Retorno: Dia del mes de la fecha(int).
    */
    public int obtenerDiaFecha(String fecha){
        // Crear un objeto DateTimeFormatter para analizar la cadena de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Convertir la cadena de fecha en un objeto LocalDate
        LocalDate localDate = LocalDate.parse(fecha, formato);

        // Obtener el día del objeto LocalDate y devolverlo como un entero
        return localDate.getDayOfMonth();
    }

    public int obtenerMesFecha(String fecha){
        // Crear un objeto DateTimeFormatter para analizar la cadena de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Convertir la cadena de fecha en un objeto LocalDate
        LocalDate localDate = LocalDate.parse(fecha, formato);

        // Obtener el día del objeto LocalDate y devolverlo como un entero
        return localDate.getMonthValue();
    }
}
