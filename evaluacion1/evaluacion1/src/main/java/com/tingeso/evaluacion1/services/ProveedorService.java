package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.ProveedorEntity;
import com.tingeso.evaluacion1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    /*
    Descripcion metodo: Metodo que crea un nuevo proveedor y lo guarda en la base de datos..
    Parametros de entrada: Codigo del proveedor(String), nombre(String), categoria(String) y retencion(String).
    Retorno: Nuevo proveedor(ProveedorEntity).
    */
    public ProveedorEntity guardarProveedor(String codigo, String nombre, String categoria, String retencion) {
        ProveedorEntity proveedor= new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        return proveedorRepository.save(proveedor);
    }

    /*
    Descripcion metodo: Metodo que obtiene la lista de proveedores registrados en la base de datos.
    Parametros de entrada: Vacio.
    Retorno: Lista con los proveedores encontrados en la base de datos(ArrayList<ProveedorEntity>).
    */
    public ArrayList<ProveedorEntity> obtenerProveedores() {
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    /*
    Descripcion metodo: Metodo que obtiene un proveedor segun su codigo.
    Parametros de entrada: Codigo del proveedor(String).
    Retorno: Proveedor(ProveedorEntity).
    */
    public ProveedorEntity obtenerPorCodigo(String codigo) {
        return proveedorRepository.findByCodigo(codigo);
    }


    /*
    Descripcion metodo: Metodo que elimina un proveedor segun su codigo.
    Parametros de entrada: Codigo del proveedor(String).
    Retorno: Vacio.
    */
    public void eliminarProveedorPorCodigo(String codigo){
        proveedorRepository.deleteById(codigo);
    }
}