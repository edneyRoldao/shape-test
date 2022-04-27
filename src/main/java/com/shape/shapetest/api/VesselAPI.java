package com.shape.shapetest.api;

import com.shape.shapetest.annotation.Log;
import com.shape.shapetest.dto.VesselOperation;
import com.shape.shapetest.model.Equipment;
import com.shape.shapetest.model.Operation;
import com.shape.shapetest.model.Vessel;
import com.shape.shapetest.service.VesselService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vessels")
@RequiredArgsConstructor
public class VesselAPI {

    private final VesselService service;

    @Log
    @PostMapping("insert-vessel")
    public ResponseEntity<String> insertVessel(@RequestBody Vessel vessel) {
        service.insertVessel(vessel);
        return ResponseEntity.status(201).body("Vessel has been created");
    }

    @Log
    @PostMapping("{vesselCode}/insert-equipment")
    public ResponseEntity<String> insertEquipment(@PathVariable String vesselCode, @RequestBody Equipment equipment) {
        service.insertEquipmentToVessel(vesselCode, equipment);
        return ResponseEntity.status(201).body("Equipment has been added to a vessel");
    }

    @Log
    @PatchMapping("disable-equipments")
    public ResponseEntity<String> inactiveEquipments(@RequestBody List<String> equipmentCodes) {
        List<String> equipmentCodesDisabled = service.disableEquipments(equipmentCodes);
        return ResponseEntity.status(200).body("The following equipments has been disabled: " + equipmentCodesDisabled.toString());
    }

    @Log
    @GetMapping("{vesselCode}/active-equipments")
    public ResponseEntity<List<Equipment>> findAllActiveEquipmentFromVessel(@PathVariable String vesselCode) {
        List<Equipment> equipmens = service.findAllActiveEquipmentFromVessel(vesselCode);
        return ResponseEntity.status(200).body(equipmens);
    }

    @Log
    @PostMapping("add-operation")
    public ResponseEntity<String> addOperationOrder(@RequestBody Operation operation) {
        service.addOperationOrder(operation);
        return ResponseEntity.status(201).body("Operation order has been created");
    }

    @Log
    @GetMapping("equipment-code/{equipmentCode}/total-cost-operations")
    public ResponseEntity<Double> getTotalOperationCostByEquipmentCode(@PathVariable String equipmentCode) {
        Double total = service.sumTotalOperationCostByEquipmentCode(equipmentCode);
        return ResponseEntity.status(200).body(total);
    }

    @Log
    @GetMapping("equipment-name/{equipmentName}/total-cost-operations")
    public ResponseEntity<Double> getTotalOperationCostByEquipmentName(@PathVariable String equipmentName) {
        Double total = service.sumTotalOperationCostByEquipmentName(equipmentName);
        return ResponseEntity.status(200).body(total);
    }

    @Log
    @GetMapping("operations/average-cost")
    public ResponseEntity<List<VesselOperation>> getAverageCostInOperationByVessel() {
        List<VesselOperation> response = service.getOperationAverageCostByVessel();
        return ResponseEntity.status(200).body(response);
    }

}
