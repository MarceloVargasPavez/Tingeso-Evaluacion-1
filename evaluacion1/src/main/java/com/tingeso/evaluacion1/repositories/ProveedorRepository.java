package com.tingeso.evaluacion1.repositories;

import com.tingeso.evaluacion1.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Repositorio de la entidad Proveedor.
*/
@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {

    public ProveedorEntity findByCodigo(String codigo);
}
