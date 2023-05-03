package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.services.AcopioLecheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AcopioLecheTests {

    @Autowired
    AcopioLecheService acopioLecheService;

    @Test
    void test1GuardarAcopioBD() {
        String fecha = "2023/04/13";
        String turno = "M";
        String codigo_proveedor = "9999";
        Integer kls_leche = 40;
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, fecha, turno, codigo_proveedor, kls_leche);
        AcopioLecheEntity acopio = acopioLecheService.guardarAcopioBD(fecha, turno, codigo_proveedor, kls_leche);
        assertEquals(acopioPrueba, acopio);
    }

    @Test
    void test1GuardarAcopio() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/13", "M", "9999", 40);
        AcopioLecheEntity acopio = acopioLecheService.guardarAcopio(acopioPrueba);
        assertEquals(acopioPrueba, acopio);
    }

    @Test
    void test1ObtenerAcopiosLeche() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/13", "M", "9999", 40);
        acopioLecheService.guardarAcopio(acopioPrueba);
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios.add(acopioPrueba);
        ArrayList<AcopioLecheEntity> acopios1 = acopioLecheService.obtenerAcopiosLeche();
        assertEquals(acopios, acopios1);
    }

    @Test
    void test1EliminarAcopios() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/13", "M", "9999", 40);
        acopioLecheService.guardarAcopio(acopioPrueba);
        System.out.println(acopioLecheService.obtenerAcopiosLeche());
        acopioLecheService.eliminarAcopios();
        System.out.println(acopioLecheService.obtenerAcopiosLeche());
    }

    @Test
    void test1ObtenerAcopiosProveedor() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/13", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba1 = new AcopioLecheEntity(2, "2023/04/13", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba2 = new AcopioLecheEntity(3, "2023/04/13", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba3 = new AcopioLecheEntity(4, "2023/04/13", "M", "99998", 40);
        AcopioLecheEntity acopioPrueba4 = new AcopioLecheEntity(5, "2023/04/13", "M", "99999", 40);
        AcopioLecheEntity acopioPrueba5 = new AcopioLecheEntity(6, "2023/04/13", "M", "99998", 40);
        acopioLecheService.guardarAcopio(acopioPrueba);
        acopioLecheService.guardarAcopio(acopioPrueba1);
        acopioLecheService.guardarAcopio(acopioPrueba2);
        acopioLecheService.guardarAcopio(acopioPrueba3);
        acopioLecheService.guardarAcopio(acopioPrueba4);
        acopioLecheService.guardarAcopio(acopioPrueba5);
        ArrayList<AcopioLecheEntity> acopiosProvedor9999=new ArrayList<>();
        acopiosProvedor9999.add(acopioPrueba);
        acopiosProvedor9999.add(acopioPrueba1);
        acopiosProvedor9999.add(acopioPrueba2);
        acopiosProvedor9999.add(acopioPrueba4);
        ArrayList<AcopioLecheEntity> acopiosProveedor=acopioLecheService.obtenerAcopiosProveedor("99999");
        assertEquals(acopiosProvedor9999,acopiosProveedor);
    }
}
