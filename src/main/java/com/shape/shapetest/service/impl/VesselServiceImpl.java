package com.shape.shapetest.service.impl;

import com.shape.shapetest.dto.VesselOperation;
import com.shape.shapetest.exceprion.EquipmentNotFoundException;
import com.shape.shapetest.exceprion.VesselNotFoundException;
import com.shape.shapetest.model.Equipment;
import com.shape.shapetest.model.Operation;
import com.shape.shapetest.model.Vessel;
import com.shape.shapetest.repository.EquipmentRepository;
import com.shape.shapetest.repository.OperationRepository;
import com.shape.shapetest.repository.VesselRepository;
import com.shape.shapetest.service.VesselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;
    private final EquipmentRepository equipmentRepository;
    private final OperationRepository operationRepository;

    @Override
    public void insertVessel(Vessel vessel) {
        log.info("stage:init VesselServiceImpl.insertVessel vessel:{}", vessel);
        vesselRepository.save(vessel);
        log.info("stage:end VesselServiceImpl.insertVessel process finished");
    }

    @Override
    public void insertEquipmentToVessel(String vesselCode, Equipment equipment) {
        log.info("stage:init VesselServiceImpl.insertEquipmentToVessel equipment:{} vesselCode:{}", equipment, vesselCode);

        Vessel vessel = vesselRepository.findByCode(vesselCode).orElseThrow(VesselNotFoundException::new);
        equipment.setVessel(vessel);

        equipmentRepository.save(equipment);

        log.info("stage:end VesselServiceImpl.insertEquipmentToVessel process finished");
    }

    @Override
    public List<String> disableEquipments(List<String> equipmentCodes) {
        log.info("stage:init VesselServiceImpl.disableEquipments equipmentCodes:{}", equipmentCodes);

        List<Equipment> equipments = equipmentRepository.findAllByCodeIn(equipmentCodes);

        equipments.forEach(equipment -> equipment.setActive(false));

        List<String> equipmentCodesDisabled = equipmentRepository.saveAll(equipments)
                .stream()
                .map(Equipment::getCode)
                .collect(Collectors.toList());

        log.info("stage:end VesselServiceImpl.disableEquipments equipmentCodesDisabled:{}", equipmentCodesDisabled);
        return equipmentCodesDisabled;
    }

    @Override
    public List<Equipment> findAllActiveEquipmentFromVessel(String vesselCode) {
        log.info("stage:init VesselServiceImpl.findAllActiveEquipmentFromVessel vesselCode:{}", vesselCode);
        List<Equipment> equipments = equipmentRepository.findAllByVesselCodeAndActiveTrue(vesselCode);

        log.info("stage:end VesselServiceImpl.findAllActiveEquipmentFromVessel equipments:{}", equipments);
        return equipments;
    }

    @Override
    public void addOperationOrder(Operation operation) {
        log.info("stage:init VesselServiceImpl.addOperationOrder operation:{}", operation);

        Equipment equipment = equipmentRepository.findByCodeAndActiveTrue(operation.getCode())
                .orElseThrow(EquipmentNotFoundException::new);

        operation.setEquipment(equipment);

        operationRepository.save(operation);

        log.info("stage:end VesselServiceImpl.addOperationOrder  - process finished");
    }

    @Override
    public Double sumTotalOperationCostByEquipmentCode(String equipmentCode) {
        log.info("stage:init VesselServiceImpl.sumTotalOperationCostByEquipmentCode equipmentCode:{}", equipmentCode);

        Double total = operationRepository.findAllByEquipmentCode(equipmentCode).stream().mapToDouble(Operation::getCost).sum();

        log.info("stage:end VesselServiceImpl.sumTotalOperationCostByEquipmentCode - process has been finished - totalCost:{}", total);
        return total;
    }

    @Override
    public Double sumTotalOperationCostByEquipmentName(String equipmentName) {
        log.info("stage:init VesselServiceImpl.sumTotalOperationCostByEquipmentName equipmentName:{}", equipmentName);

        Double total = operationRepository.findAllByEquipmentName(equipmentName).stream().mapToDouble(Operation::getCost).sum();

        log.info("stage:end VesselServiceImpl.sumTotalOperationCostByEquipmentName - process has been finished - totalCost:{}", total);
        return total;
    }

    @Override
    public List<VesselOperation> getOperationAverageCostByVessel() {
        log.info("stage:init VesselServiceImpl.getOperationAverageCostByVessel");
        List<VesselOperation> result = new ArrayList<>();

        vesselRepository.findAll().forEach(vessel -> {

            List<Long> equipmentIds = equipmentRepository.findAllByVesselCodeAndActiveTrue(vessel.getCode())
                    .stream().map(Equipment::getId)
                    .collect(Collectors.toList());

            List<Operation> operations = operationRepository.findAllByEquipmentIdIn(equipmentIds);
            Double average = operations.stream().mapToDouble(Operation::getCost).average().orElse(Double.NaN);

            result.add(VesselOperation
                    .builder()
                    .vesselCode(vessel.getCode())
                    .averageOperation(average)
                    .totalOperation(operations.size())
                    .build());
        });

        log.info("stage:end VesselServiceImpl.getOperationAverageCostByVessel - process has been finished");
        return result;
    }

}
