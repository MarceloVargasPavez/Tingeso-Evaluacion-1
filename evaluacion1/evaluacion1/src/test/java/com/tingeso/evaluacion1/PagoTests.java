package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.services.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
class PagoTests {

    @Autowired
    PagoService pagoService;

    @Test
    void test1EstablecerQuincena() {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        LocalDate fecha1 = LocalDate.of(2023, 1, 5);
        LocalDate fecha2 = LocalDate.of(2023, 1, 8);
        fechas.add(fecha1);
        fechas.add(fecha2);
        LocalDate quincena = pagoService.establecerQuincena(fechas);
        LocalDate quincenaEsperada=LocalDate.of(2023,1,15);
        assertEquals(quincenaEsperada,quincena);
    }

    @Test
    void test2EstablecerQuincena() {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        LocalDate quincena = pagoService.establecerQuincena(fechas);
        LocalDate quincenaEsperada=LocalDate.of(2023,1,15);
        assertEquals(quincenaEsperada,quincena);
    }

    @Test
    void testTotalKlsLeche(){
        /*int totalKlsLeche= pagoService.totalKlsLeche();*/
    }
}
