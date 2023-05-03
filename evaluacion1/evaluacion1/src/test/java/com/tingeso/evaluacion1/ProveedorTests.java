package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.ProveedorEntity;
import com.tingeso.evaluacion1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProveedorTests {

    @Autowired
    ProveedorService proveedorService;

    @Test
    void test1GuardarProveedor() {
        String codigo = "99999";
        String nombre = "Juan";
        String categoria = "A";
        String retencion = "SI";
        ProveedorEntity proveedorPrueba = new ProveedorEntity(codigo, nombre, categoria, retencion);
        ProveedorEntity proveedor1 = proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        assertEquals(proveedorPrueba, proveedor1);
    }

    @Test
    void test1ObtenerProveedores() {
        ArrayList<ProveedorEntity> listaProveedores = new ArrayList<>();
        ProveedorEntity proveedorPrueba = new ProveedorEntity("99999", "Juan", "A", "SI");
        listaProveedores.add(proveedorPrueba);
        proveedorService.guardarProveedor(proveedorPrueba.getCodigo(), proveedorPrueba.getNombre(),
                proveedorPrueba.getCategoria(), proveedorPrueba.getRetencion());
        ArrayList<ProveedorEntity> listaProveedores1 = proveedorService.obtenerProveedores();
        assertEquals(listaProveedores, listaProveedores1);
    }

    @Test
    void test1ObtenerPorCodigo() {
        ProveedorEntity proveedorPrueba = proveedorService.guardarProveedor("99999", "Juan", "A", "SI");
        ProveedorEntity proveedor = proveedorService.obtenerPorCodigo("99999");
        assertEquals(proveedorPrueba, proveedor);
    }

    @Test
    void test1EliminarProveedorPorCodigo() {

        proveedorService.guardarProveedor("99999", "Juan", "A", "SI");
        System.out.println(proveedorService.obtenerPorCodigo("99999"));
        proveedorService.eliminarProveedorPorCodigo("99999");
        System.out.println(proveedorService.obtenerPorCodigo("99999"));
    }
}
