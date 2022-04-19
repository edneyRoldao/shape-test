package com.shape.shapetest.repository;

import com.shape.shapetest.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findAllByCode(String code);

}
