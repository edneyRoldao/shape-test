package com.shape.shapetest.configuration;

import com.shape.shapetest.exceprion.EquipmentNotFoundException;
import com.shape.shapetest.exceprion.VesselNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<String> handleUniqueCodeError() {
        return ResponseEntity.badRequest().body("the field code already exists and it should be unique");
    }

    @ExceptionHandler(VesselNotFoundException.class)
    public final ResponseEntity<String> handleVesselNotFoundException() {
        return ResponseEntity.badRequest().body("the vesselId does not exist");
    }

    @ExceptionHandler(EquipmentNotFoundException.class)
    public final ResponseEntity<String> handleEquipmentNotFoundException() {
        return ResponseEntity.badRequest().body("the equipmet does exist or it is inactive");
    }

}

