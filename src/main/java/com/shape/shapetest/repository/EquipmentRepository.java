package com.shape.shapetest.repository;

import com.shape.shapetest.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByCodeIn(List<String> codes);

    List<Equipment> findAllByVesselCodeAndActiveTrue(String vesselCode);

    Optional<Equipment> findByCodeAndActiveTrue(String code);

}
