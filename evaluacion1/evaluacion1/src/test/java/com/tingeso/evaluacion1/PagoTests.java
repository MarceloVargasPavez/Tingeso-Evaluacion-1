package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.entities.PagoEntity;
import com.tingeso.evaluacion1.services.PagoService;
import com.tingeso.evaluacion1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
class PagoTests {

    @Autowired
    PagoService pagoService;

    @Autowired
    ProveedorService proveedorService;

    @Test
    void test1GuardaPago() {
        PagoEntity pago = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 350, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        PagoEntity pago1 = pagoService.guardarPago(pago);
        assertEquals(pago, pago1);
    }

    @Test
    void test1ObtenerPagos() {
        PagoEntity pago = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 350, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        pagoService.guardarPago(pago);
        ArrayList<PagoEntity> pagos = new ArrayList<>();
        pagos.add(pago);
        ArrayList<PagoEntity> pagos1 = pagoService.obtenerPagos();
        assertEquals(pagos, pagos1);
    }

    @Test
    void test1EstablecerQuincena() {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        LocalDate fecha = LocalDate.of(2023, 1, 1);
        LocalDate fecha1 = LocalDate.of(2023, 1, 3);
        LocalDate fecha2 = LocalDate.of(2023, 1, 4);
        LocalDate fecha3 = LocalDate.of(2023, 1, 5);
        LocalDate fecha4 = LocalDate.of(2023, 1, 7);
        LocalDate fecha5 = LocalDate.of(2023, 1, 10);
        LocalDate fecha6 = LocalDate.of(2023, 1, 14);
        fechas.add(fecha);
        fechas.add(fecha1);
        fechas.add(fecha2);
        fechas.add(fecha3);
        fechas.add(fecha4);
        fechas.add(fecha5);
        fechas.add(fecha6);
        LocalDate quincena = pagoService.establecerQuincena(fechas);
        LocalDate quincenaEsperada = LocalDate.of(2023, 1, 15);
        assertEquals(quincenaEsperada, quincena);
    }

    @Test
    void test2EstablecerQuincena() {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        LocalDate quincena = pagoService.establecerQuincena(fechas);
        LocalDate quincenaEsperada = LocalDate.of(2023, 1, 15);
        assertEquals(quincenaEsperada, quincena);
    }

    @Test
    void test1TotalKlsLeche() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/01", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba1 = new AcopioLecheEntity(2, "2023/04/05", "T", "99999", 34);
        AcopioLecheEntity acopioPrueba2 = new AcopioLecheEntity(3, "2023/04/07", "T", "99999", 54);
        AcopioLecheEntity acopioPrueba3 = new AcopioLecheEntity(4, "2023/04/09", "M", "99999", 50);
        AcopioLecheEntity acopioPrueba4 = new AcopioLecheEntity(5, "2023/04/10", "T", "99999", 46);
        AcopioLecheEntity acopioPrueba5 = new AcopioLecheEntity(6, "2023/04/14", "M", "99999", 39);
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios.add(acopioPrueba);
        acopios.add(acopioPrueba1);
        acopios.add(acopioPrueba2);
        acopios.add(acopioPrueba3);
        acopios.add(acopioPrueba4);
        acopios.add(acopioPrueba5);
        int total_klsLeche = pagoService.totalKlsLeche(acopios);
        assertEquals(263, total_klsLeche, 0.0);
    }

    @Test
    void test1CantidadDiasEnvio() {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        LocalDate fecha = LocalDate.of(2023, 1, 1);
        LocalDate fecha1 = LocalDate.of(2023, 1, 3);
        LocalDate fecha2 = LocalDate.of(2023, 1, 4);
        LocalDate fecha3 = LocalDate.of(2023, 1, 5);
        LocalDate fecha4 = LocalDate.of(2023, 1, 7);
        LocalDate fecha5 = LocalDate.of(2023, 1, 10);
        LocalDate fecha6 = LocalDate.of(2023, 1, 14);
        fechas.add(fecha);
        fechas.add(fecha1);
        fechas.add(fecha2);
        fechas.add(fecha3);
        fechas.add(fecha4);
        fechas.add(fecha5);
        fechas.add(fecha6);
        int cantidad_dias = pagoService.cantidadDiasEnvio(fechas);
        assertEquals(7, cantidad_dias, 0.0);
    }

    @Test
    void test1PromedioDiarioLeche() {
        float total_klsLeche = 340;
        LocalDate quincena = LocalDate.of(2023, 12, 15);
        float promedio = pagoService.promedioDiarioLeche(total_klsLeche, quincena);
        assertEquals(22.6, promedio, 0.1);
    }

    @Test
    void test2PromedioDiarioLeche() {
        float total_klsLeche = 340;
        LocalDate quincena = null;
        float promedio = pagoService.promedioDiarioLeche(total_klsLeche, quincena);
        assertEquals(0, promedio, 0.0);
    }

    @Test
    void test1TransformarFechas() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/01", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba1 = new AcopioLecheEntity(2, "2023/04/05", "T", "99999", 34);
        AcopioLecheEntity acopioPrueba2 = new AcopioLecheEntity(3, "2023/04/07", "T", "99999", 54);
        AcopioLecheEntity acopioPrueba3 = new AcopioLecheEntity(4, "2023/04/09", "M", "99999", 50);
        AcopioLecheEntity acopioPrueba4 = new AcopioLecheEntity(5, "2023/04/10", "T", "99999", 46);
        AcopioLecheEntity acopioPrueba5 = new AcopioLecheEntity(6, "2023/04/14", "M", "99999", 39);
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios.add(acopioPrueba);
        acopios.add(acopioPrueba1);
        acopios.add(acopioPrueba2);
        acopios.add(acopioPrueba3);
        acopios.add(acopioPrueba4);
        acopios.add(acopioPrueba5);
        ArrayList<LocalDate> fechas = new ArrayList<>();
        fechas.add(LocalDate.of(2023, 4, 1));
        fechas.add(LocalDate.of(2023, 4, 5));
        fechas.add(LocalDate.of(2023, 4, 7));
        fechas.add(LocalDate.of(2023, 4, 9));
        fechas.add(LocalDate.of(2023, 4, 10));
        fechas.add(LocalDate.of(2023, 4, 14));
        ArrayList<LocalDate> fechas1 = pagoService.transformarfechas(acopios);
        assertEquals(fechas, fechas1);
    }

    @Test
    void test1CalcularVariacion() {
        float valor_actual = 10;
        float valor_anterior = 5;
        float variacion = pagoService.calculoVariacion(valor_actual, valor_anterior);
        assertEquals(100, variacion, 0.0);
    }

    @Test
    void test1PorcentajeVariacionLeche() {
        PagoEntity pago_anterior = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 350, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        float porcentaje = pagoService.porcentajeVariacionLeche(200, pago_anterior);
        assertEquals(-42.8, porcentaje, 0.1);
    }

    @Test
    void test1PorcentajeVariacionGrasa() {
        PagoEntity pago_anterior = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        float porcentaje = pagoService.porcentajeVariacionGrasa(40, pago_anterior);
        assertEquals(14.2, porcentaje, 0.1);
    }

    @Test
    void test1PorcentajeVariacionSolidos() {
        PagoEntity pago_anterior = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        float porcentaje = pagoService.porcentajeVariacionSolidos(13, pago_anterior);
        assertEquals(-13.3, porcentaje, 0.1);
    }

    @Test
    void test1ObtenerPagoQuincenaAnterior() {
        PagoEntity pago = new PagoEntity(1, LocalDate.of(2023, 1, 15), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        PagoEntity pago1 = new PagoEntity(2, LocalDate.of(2023, 1, 31), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        pagoService.guardarPago(pago);
        pagoService.guardarPago(pago1);
        PagoEntity pago_anterior = pagoService.obtenerPagoQuincenaAnterior("99999", LocalDate.of(2023,
                2, 15));
        assertEquals(pago1, pago_anterior);

    }

    @Test
    void test2ObtenerPagoQuincenaAnterior() {
        PagoEntity pago = new PagoEntity(1, LocalDate.of(2022, 12, 15), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        PagoEntity pago1 = new PagoEntity(2, LocalDate.of(2022, 12, 31), "99999",
                "juan", 200, 10, 35, 0,
                35, 0, 15, 0,
                0, 0, 0, 0,
                0, 0, 0,
                0, 0, 0);
        pagoService.guardarPago(pago);
        pagoService.guardarPago(pago1);
        PagoEntity pago_anterior = pagoService.obtenerPagoQuincenaAnterior("99999", LocalDate.of(2023,
                1, 15));
        assertEquals(pago1, pago_anterior);
    }

    @Test
    void test3ObtenerPagoQuincenaAnterior() {
        PagoEntity pago_anterior = pagoService.obtenerPagoQuincenaAnterior("99999", LocalDate.of(2023,
                1, 15));
        assertNull(pago_anterior);
    }

    @Test
    void test1PorcentajeDescuentoVariacionLeche() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionLeche(-35);
        assertEquals(15, porcentaje, 0.0);
    }

    @Test
    void test2PorcentajeDescuentoVariacionLeche() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionLeche(-12);
        assertEquals(7, porcentaje, 0.0);
    }

    @Test
    void test3PorcentajeDescuentoVariacionLeche() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionLeche(-50);
        assertEquals(30, porcentaje, 0.0);
    }

    @Test
    void test1PorcentajeDescuentoVariacionGrasa() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionGrasa(-17);
        assertEquals(12, porcentaje, 0.0);
    }

    @Test
    void test2PorcentajeDescuentoVariacionGrasa() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionGrasa(-27);
        assertEquals(20, porcentaje, 0.0);
    }

    @Test
    void test3PorcentajeDescuentoVariacionGrasa() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionGrasa(-68);
        assertEquals(30, porcentaje, 0.0);
    }

    @Test
    void test1PorcentajeDescuentoVariacionSolidos() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionSolidos(-10);
        assertEquals(18, porcentaje, 0.0);
    }

    @Test
    void test2PorcentajeDescuentoVariacionSolidos() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionSolidos(-30);
        assertEquals(27, porcentaje, 0.0);
    }

    @Test
    void test3PorcentajeDescuentoVariacionSolidos() {
        float porcentaje = pagoService.porcentajeDescuentoVariacionSolidos(-67);
        assertEquals(45, porcentaje, 0.0);
    }

    @Test
    void test1PagoPorLeche() {
        int pago_por_leche = pagoService.pagoPorLeche("A", 500);
        assertEquals(350000, pago_por_leche, 0.0);
    }

    @Test
    void test2PagoPorLeche() {
        int pago_por_leche = pagoService.pagoPorLeche("B", 500);
        assertEquals(275000, pago_por_leche, 0.0);
    }

    @Test
    void test3PagoPorLeche() {
        int pago_por_leche = pagoService.pagoPorLeche("C", 500);
        assertEquals(200000, pago_por_leche, 0.0);
    }

    @Test
    void test4PagoPorLeche() {
        int pago_por_leche = pagoService.pagoPorLeche("D", 500);
        assertEquals(125000, pago_por_leche, 0.0);
    }

    @Test
    void test1PagoPorGrasa() {
        int pago_por_grasa = pagoService.pagoPorGrasa(10, 500);
        assertEquals(15000, pago_por_grasa, 0.0);
    }

    @Test
    void test2PagoPorGrasa() {
        int pago_por_grasa = pagoService.pagoPorGrasa(30, 500);
        assertEquals(40000, pago_por_grasa, 0.0);
    }

    @Test
    void test3PagoPorGrasa() {
        int pago_por_grasa = pagoService.pagoPorGrasa(50, 500);
        assertEquals(60000, pago_por_grasa, 0.0);
    }

    @Test
    void test1PagoPorSolidos() {
        int pago_por_solidos = pagoService.pagoPorSolidos(5, 500);
        assertEquals(-65000, pago_por_solidos, 0.0);
    }

    @Test
    void test2PagoPorSolidos() {
        int pago_por_solidos = pagoService.pagoPorSolidos(15, 500);
        assertEquals(-45000, pago_por_solidos, 0.0);
    }

    @Test
    void test3PagoPorSolidos() {
        int pago_por_solidos = pagoService.pagoPorSolidos(30, 500);
        assertEquals(47500, pago_por_solidos, 0.0);
    }

    @Test
    void test4PagoPorSolidos() {
        int pago_por_solidos = pagoService.pagoPorSolidos(40, 500);
        assertEquals(75000, pago_por_solidos, 0.0);
    }

    @Test
    void test1CalculoBonificacionFrecuencia() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/01", "T", "99999", 40);
        AcopioLecheEntity acopioPrueba1 = new AcopioLecheEntity(2, "2023/04/02", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba2 = new AcopioLecheEntity(3, "2023/04/02", "T", "99999", 40);
        AcopioLecheEntity acopioPrueba3 = new AcopioLecheEntity(4, "2023/04/03", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba4 = new AcopioLecheEntity(5, "2023/04/03", "T", "99999", 40);
        AcopioLecheEntity acopioPrueba5 = new AcopioLecheEntity(6, "2023/04/04", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba6 = new AcopioLecheEntity(7, "2023/04/04", "T", "99999", 40);
        AcopioLecheEntity acopioPrueba7 = new AcopioLecheEntity(8, "2023/04/05", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba8 = new AcopioLecheEntity(9, "2023/04/05", "T", "99999", 34);
        AcopioLecheEntity acopioPrueba9 = new AcopioLecheEntity(10, "2023/04/07", "T", "99999", 54);
        AcopioLecheEntity acopioPrueba10 = new AcopioLecheEntity(11, "2023/04/09", "T", "99999", 50);
        AcopioLecheEntity acopioPrueba11 = new AcopioLecheEntity(12, "2023/04/10", "T", "99999", 46);
        AcopioLecheEntity acopioPrueba12 = new AcopioLecheEntity(13, "2023/04/14", "M", "99999", 39);
        AcopioLecheEntity acopioPrueba13 = new AcopioLecheEntity(14, "2023/04/14", "T", "99999", 40);
        AcopioLecheEntity acopioPrueba14 = new AcopioLecheEntity(15, "2023/04/15", "T", "99999", 40);
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios.add(acopioPrueba);
        acopios.add(acopioPrueba1);
        acopios.add(acopioPrueba2);
        acopios.add(acopioPrueba3);
        acopios.add(acopioPrueba4);
        acopios.add(acopioPrueba5);
        acopios.add(acopioPrueba6);
        acopios.add(acopioPrueba7);
        acopios.add(acopioPrueba8);
        acopios.add(acopioPrueba9);
        acopios.add(acopioPrueba10);
        acopios.add(acopioPrueba11);
        acopios.add(acopioPrueba12);
        acopios.add(acopioPrueba13);
        acopios.add(acopioPrueba14);
        ArrayList<LocalDate> fechas = new ArrayList<>();
        fechas.add(LocalDate.of(2023, 4, 1));
        fechas.add(LocalDate.of(2023, 4, 2));
        fechas.add(LocalDate.of(2023, 4, 2));
        fechas.add(LocalDate.of(2023, 4, 3));
        fechas.add(LocalDate.of(2023, 4, 3));
        fechas.add(LocalDate.of(2023, 4, 4));
        fechas.add(LocalDate.of(2023, 4, 4));
        fechas.add(LocalDate.of(2023, 4, 5));
        fechas.add(LocalDate.of(2023, 4, 5));
        fechas.add(LocalDate.of(2023, 4, 7));
        fechas.add(LocalDate.of(2023, 4, 9));
        fechas.add(LocalDate.of(2023, 4, 10));
        fechas.add(LocalDate.of(2023, 4, 14));
        fechas.add(LocalDate.of(2023, 4, 14));
        fechas.add(LocalDate.of(2023, 4, 15));
        LocalDate quincena = LocalDate.of(2023, 4, 15);
        float bonificacion = pagoService.calculoBonificaionFrecuencia(acopios, fechas, quincena);
        assertEquals(8, bonificacion, 0.0);
    }

    @Test
    void test1PagoAcopioLeche() {
        float resultado = pagoService.pagoAcopioLeche(350000, 45000, 30000, 12);
        assertEquals(467000, resultado, 0.0);
    }

    @Test
    void test1Descuentos() {
        float descuentos = pagoService.descuentos(467000, 18, 12, 7);
        assertEquals(-172790, descuentos, 0.0);
    }

    @Test
    void test1PagoTotal() {
        float pago_total = pagoService.pagoTotal(467000, -172790);
        assertEquals(294210, pago_total, 0.0);
    }

    @Test
    void test1PagoFinal() {
        float pago_final = pagoService.pagoFinal(293210, 0);
        assertEquals(293210, pago_final, 0.0);
    }
}
