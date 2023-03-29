package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.ProveedorEntity;
import com.tingeso.evaluacion1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public List<ProveedorEntity> obtenerProveedores() {
        return proveedorRepository.findAll();
    }

    public ProveedorEntity guardarProveedor(ProveedorEntity proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Optional<ProveedorEntity> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public ProveedorEntity obtenerPorCodigo(String codigo) {
        return proveedorRepository.findByCodigo(codigo);
    }

    public Boolean eliminarProveedor(Long id) {
        try {
            proveedorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
