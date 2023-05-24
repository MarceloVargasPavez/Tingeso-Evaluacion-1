package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.services.AcopioLecheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(null, fecha, turno, codigo_proveedor, kls_leche);
        AcopioLecheEntity acopio = acopioLecheService.guardarAcopioBD(fecha, turno, codigo_proveedor, kls_leche);
        assertEquals(acopioPrueba.getFecha(), acopio.getFecha());
        assertEquals(acopioPrueba.getTurno(), acopio.getTurno());
        assertEquals(acopioPrueba.getCodigo_proveedor(), acopio.getCodigo_proveedor());
        assertEquals(acopioPrueba.getKilos_leche(), acopio.getKilos_leche());
    }

    @Test
    void test1GuardarAcopio() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(1, "2023/04/13", "M", "9999", 40);
        AcopioLecheEntity acopio = acopioLecheService.guardarAcopio(acopioPrueba);
        assertEquals(acopioPrueba.getFecha(), acopio.getFecha());
        assertEquals(acopioPrueba.getTurno(), acopio.getTurno());
        assertEquals(acopioPrueba.getCodigo_proveedor(), acopio.getCodigo_proveedor());
        assertEquals(acopioPrueba.getKilos_leche(), acopio.getKilos_leche());
    }

    @Test
    void test1ObtenerAcopiosLeche() {
        AcopioLecheEntity acopioPrueba = acopioLecheService.guardarAcopioBD("2023/04/13", "M", "9999", 40);
        ArrayList<AcopioLecheEntity> acopios = acopioLecheService.obtenerAcopiosLeche();
        assertTrue(acopios.contains(acopioPrueba));
    }

    @Test
    void test1EliminarAcopios() {
        AcopioLecheEntity acopioPrueba = new AcopioLecheEntity(99999, "2023/04/13", "M", "9999", 40);
        acopioLecheService.guardarAcopio(acopioPrueba);
        acopioLecheService.eliminarAcopios();
        ArrayList<AcopioLecheEntity> acopios = acopioLecheService.obtenerAcopiosLeche();
        assertTrue(acopios.isEmpty());
    }

    @Test
    void test1ObtenerAcopiosProveedor() {
        acopioLecheService.eliminarAcopios();
        // Crear acopios para diferentes proveedores
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99999", 40);
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99999", 40);
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99999", 40);
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99998", 40);
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99999", 40);
        acopioLecheService.guardarAcopioBD("2023/04/13", "M", "99998", 40);
        // Obtener acopios del proveedor 99999
        ArrayList<AcopioLecheEntity> acopiosProvedor9999 = acopioLecheService.obtenerAcopiosProveedor("99999");
        // Verificar que los acopios obtenidos corresponden al proveedor 99999
        for (AcopioLecheEntity acopio : acopiosProvedor9999) {
            assertEquals("99999", acopio.getCodigo_proveedor());
        }
        // Verificar que se obtienen todos los acopios del proveedor 99999
        assertEquals(4, acopiosProvedor9999.size());
    }
}
