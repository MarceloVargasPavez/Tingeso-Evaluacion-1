package com.tingeso.evaluacion1.repositories;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AcopioLecheRepository extends JpaRepository <AcopioLecheEntity, Integer> {


}

