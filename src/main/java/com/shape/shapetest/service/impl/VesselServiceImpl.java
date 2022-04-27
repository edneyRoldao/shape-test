package com.shape.shapetest.service.impl;

import com.shape.shapetest.annotation.Log;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VesselServiceImpl implements VesselService {

    private final VesselRepository vesselRepository;
    private final EquipmentRepository equipmentRepository;
    private final OperationRepository operationRepository;

    @Log
    @Override
    public void insertVessel(Vessel vessel) {
        vesselRepository.save(vessel);
    }

    @Log
    @Override
    public void insertEquipmentToVessel(String vesselCode, Equipment equipment) {
        Vessel vessel = vesselRepository.findByCode(vesselCode).orElseThrow(VesselNotFoundException::new);
        equipment.setVessel(vessel);

        equipmentRepository.save(equipment);
    }

    @Log
    @Override
    public List<String> disableEquipments(List<String> equipmentCodes) {
        List<Equipment> equipments = equipmentRepository.findAllByCodeIn(equipmentCodes);

        equipments.forEach(equipment -> equipment.setActive(false));

        return equipmentRepository.saveAll(equipments)
                .stream()
                .map(Equipment::getCode)
                .collect(Collectors.toList());
    }

    @Log
    @Override
    public List<Equipment> findAllActiveEquipmentFromVessel(String vesselCode) {
        return equipmentRepository.findAllByVesselCodeAndActiveTrue(vesselCode);
    }

    @Log
    @Override
    public void addOperationOrder(Operation operation) {
        Equipment equipment = equipmentRepository.findByCodeAndActiveTrue(operation.getCode())
                .orElseThrow(EquipmentNotFoundException::new);

        operation.setEquipment(equipment);

        operationRepository.save(operation);
    }

    @Log
    @Override
    public Double sumTotalOperationCostByEquipmentCode(String equipmentCode) {
        return operationRepository.findAllByEquipmentCode(equipmentCode).stream().mapToDouble(Operation::getCost).sum();
    }

    @Log
    @Override
    public Double sumTotalOperationCostByEquipmentName(String equipmentName) {
        return operationRepository.findAllByEquipmentName(equipmentName).stream().mapToDouble(Operation::getCost).sum();
    }

    @Log
    @Override
    public List<VesselOperation> getOperationAverageCostByVessel() {
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

        return result;
    }

}
