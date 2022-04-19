package com.shape.shapetest.api;

import com.shape.shapetest.dto.VesselOperation;
import com.shape.shapetest.model.Equipment;
import com.shape.shapetest.model.Operation;
import com.shape.shapetest.model.Vessel;
import com.shape.shapetest.service.VesselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/vessels")
@RequiredArgsConstructor
public class VesselAPI {

    private final VesselService service;

    @PostMapping("insert-vessel")
    public ResponseEntity<String> insertVessel(@RequestBody Vessel vessel) {
        log.info("stage:init VesselAPI.insertVessel vessel:{}", vessel);
        service.insertVessel(vessel);

        log.info("stage:end VesselAPI.insertVessel - vessel has been created");
        return ResponseEntity.status(201).body("Vessel has been created");
    }

    @PostMapping("{vesselCode}/insert-equipment")
    public ResponseEntity<String> insertEquipment(@PathVariable String vesselCode, @RequestBody Equipment equipment) {
        log.info("stage:init VesselAPI.insertEquipment equipment:{} vesselCode:{}", equipment, vesselCode);
        service.insertEquipmentToVessel(vesselCode, equipment);

        log.info("stage:end VesselAPI.insertEquipment - equipment has been created");
        return ResponseEntity.status(201).body("Equipment has been added to a vessel");
    }

    @PatchMapping("disable-equipments")
    public ResponseEntity<String> inactiveEquipments(@RequestBody List<String> equipmentCodes) {
        log.info("stage:init VesselAPI.inactiveEquipments equipmentCodes:{}", equipmentCodes);
        List<String> equipmentCodesDisabled = service.disableEquipments(equipmentCodes);

        log.info("stage:end VesselAPI.inactiveEquipments - process finished");
        return ResponseEntity.status(200).body("The following equipments has been disabled: " + equipmentCodesDisabled.toString());
    }

    @GetMapping("{vesselCode}/active-equipments")
    public ResponseEntity<List<Equipment>> findAllActiveEquipmentFromVessel(@PathVariable String vesselCode) {
        log.info("stage:init VesselAPI.findAllActiveEquipmentFromVessel vesselCode:{}", vesselCode);
        List<Equipment> equipmens = service.findAllActiveEquipmentFromVessel(vesselCode);

        log.info("stage:end VesselAPI.findAllActiveEquipmentFromVessel - process finished - equipmets:{}", equipmens);
        return ResponseEntity.status(200).body(equipmens);
    }

    @PostMapping("add-operation")
    public ResponseEntity<String> addOperationOrder(@RequestBody Operation operation) {
        log.info("stage:init VesselAPI.addOperationOrder operation:{}", operation);
        service.addOperationOrder(operation);

        log.info("stage:end VesselAPI.addOperationOrder - process has been finished");
        return ResponseEntity.status(201).body("Operation order has been created");
    }

    @GetMapping("equipment-code/{equipmentCode}/total-cost-operations")
    public ResponseEntity<Double> getTotalOperationCostByEquipmentCode(@PathVariable String equipmentCode) {
        log.info("stage:init VesselAPI.getTotalOperationCostByEquipmentCode equipmentCode:{}", equipmentCode);
        Double total = service.sumTotalOperationCostByEquipmentCode(equipmentCode);

        log.info("stage:end VesselAPI.getTotalOperationCostByEquipmentCode - process has been finished - totalCost:{}", total);
        return ResponseEntity.status(200).body(total);
    }

    @GetMapping("equipment-name/{equipmentName}/total-cost-operations")
    public ResponseEntity<Double> getTotalOperationCostByEquipmentName(@PathVariable String equipmentName) {
        log.info("stage:init VesselAPI.getTotalOperationCostByEquipmentName equipmentName:{}", equipmentName);
        Double total = service.sumTotalOperationCostByEquipmentName(equipmentName);

        log.info("stage:end VesselAPI.getTotalOperationCostByEquipmentName - process has been finished - totalCost:{}", total);
        return ResponseEntity.status(200).body(total);
    }

    @GetMapping("operations/average-cost")
    public ResponseEntity<List<VesselOperation>> getAverageCostInOperationByVessel() {
        log.info("stage:init VesselAPI.getAverageCostInOperationByVessel ");
        List<VesselOperation> response = service.getOperationAverageCostByVessel();

        log.info("stage:end VesselAPI.getTotalOperationCostByEquipmentName - process has been finished");
        return ResponseEntity.status(200).body(response);
    }

}
