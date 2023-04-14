package com.tingeso.evaluacion1.repositories;

import com.tingeso.evaluacion1.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Integer> {
}
