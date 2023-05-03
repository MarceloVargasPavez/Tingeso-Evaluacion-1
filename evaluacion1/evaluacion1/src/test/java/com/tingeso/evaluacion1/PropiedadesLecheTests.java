package com.tingeso.evaluacion1;

import com.tingeso.evaluacion1.entities.PropiedadesLecheEntity;
import com.tingeso.evaluacion1.services.PropiedadesLecheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PropiedadesLecheTests {

    @Autowired
    PropiedadesLecheService propiedadesLecheService;

    @Test
    void test1GuardarPropiedadesBD() {
        String codigo_proveedor = "99999";
        Integer porcentaje_solidos = 15;
        Integer porcentaje_grasa = 20;
        PropiedadesLecheEntity propiedadesLeche = new PropiedadesLecheEntity(1, codigo_proveedor, porcentaje_solidos, porcentaje_grasa);
        PropiedadesLecheEntity propiedadesLeche1 = propiedadesLecheService.guardarPropiedadesLecheBD(codigo_proveedor, porcentaje_solidos, porcentaje_grasa);
        assertEquals(propiedadesLeche, propiedadesLeche1);
    }

    @Test
    void test1GuardarPropiedades() {
        PropiedadesLecheEntity propiedadesLeche = new PropiedadesLecheEntity(1, "99999", 15, 20);
        PropiedadesLecheEntity propiedadesLeche1 = propiedadesLecheService.guardarPropiedades(propiedadesLeche);
        assertEquals(propiedadesLeche, propiedadesLeche1);
    }

    @Test
    void test1ObtenerPropiedadesLeched() {
        PropiedadesLecheEntity propiedadesLeche = new PropiedadesLecheEntity(1, "99999", 15, 20);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche);
        ArrayList<PropiedadesLecheEntity> lista_propiedades = new ArrayList<>();
        lista_propiedades.add(propiedadesLeche);
        ArrayList<PropiedadesLecheEntity> lista_propiedades1 = propiedadesLecheService.obtenerPropiedadesLeche();
        assertEquals(lista_propiedades, lista_propiedades1);
    }

    @Test
    void test1EliminarPropiedades() {
        PropiedadesLecheEntity propiedadesLeche = new PropiedadesLecheEntity(1, "99999", 15, 20);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche);
        System.out.println(propiedadesLecheService.obtenerPropiedadesLeche());
        propiedadesLecheService.eliminarPropiedades();
        System.out.println(propiedadesLecheService.obtenerPropiedadesLeche());
    }

    @Test
    void test1ObtenerPropiedadesProveedor() {
        PropiedadesLecheEntity propiedadesLeche = new PropiedadesLecheEntity(1, "99999", 15, 20);
        PropiedadesLecheEntity propiedadesLeche1 = new PropiedadesLecheEntity(2, "99998", 14, 21);
        PropiedadesLecheEntity propiedadesLeche2 = new PropiedadesLecheEntity(3, "99996", 13, 22);
        PropiedadesLecheEntity propiedadesLeche3 = new PropiedadesLecheEntity(4, "99997", 12, 23);
        PropiedadesLecheEntity propiedadesLeche4 = new PropiedadesLecheEntity(5, "99990", 11, 24);
        PropiedadesLecheEntity propiedadesLeche5 = new PropiedadesLecheEntity(6, "99992", 10, 25);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche1);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche2);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche3);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche4);
        propiedadesLecheService.guardarPropiedades(propiedadesLeche5);
        PropiedadesLecheEntity propiedades1=propiedadesLecheService.obtenerPropiedadesProveedor("99997");
        assertEquals(propiedadesLeche3,propiedades1);

    }
}