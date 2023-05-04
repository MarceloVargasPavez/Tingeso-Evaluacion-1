package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.ProveedorEntity;
import com.tingeso.evaluacion1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(proveedorPrueba.getCodigo(), proveedor1.getCodigo());
        assertEquals(proveedorPrueba.getNombre(), proveedor1.getNombre());
        assertEquals(proveedorPrueba.getCategoria(), proveedor1.getCategoria());
        assertEquals(proveedorPrueba.getRetencion(), proveedor1.getRetencion());
    }

    @Test
    void test1ObtenerProveedores() {
        ProveedorEntity proveedorPrueba=proveedorService.guardarProveedor("99999", "Juan",
                "A", "SI");
        ArrayList<ProveedorEntity> listaProveedores = proveedorService.obtenerProveedores();
        assertTrue(listaProveedores.contains(proveedorPrueba));
    }

    @Test
    void test1ObtenerPorCodigo() {
        ProveedorEntity proveedorPrueba = proveedorService.guardarProveedor("99999", "Juan", "A", "SI");
        ProveedorEntity proveedor = proveedorService.obtenerPorCodigo("99999");
        assertEquals(proveedorPrueba, proveedor);
    }

    @Test
    void test1EliminarProveedorPorCodigo() {
        String codigo = "99999";
        proveedorService.guardarProveedor(codigo, "Juan", "A", "SI");
        ProveedorEntity proveedorAntes = proveedorService.obtenerPorCodigo(codigo);
        assertNotNull(proveedorAntes); //verifica que el proveedor existe antes de eliminarlo
        proveedorService.eliminarProveedorPorCodigo(codigo);
        ProveedorEntity proveedorDespues = proveedorService.obtenerPorCodigo(codigo);
        assertNull(proveedorDespues); //verifica que el proveedor ya no existe después de eliminarlo
    }

}
