package com.shape.shapetest.repository;

import com.shape.shapetest.model.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VesselRepository extends JpaRepository<Vessel, Long> {

    Optional<Vessel> findByCode(String code);

}
