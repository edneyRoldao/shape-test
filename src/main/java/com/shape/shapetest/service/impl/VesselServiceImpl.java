package com.shape.shapetest.service.impl;

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

        equipmentRepository.findByCodeAndActiveTrue(operation.getCode())
                .orElseThrow(EquipmentNotFoundException::new);

        operationRepository.save(operation);

        log.info("stage:end VesselServiceImpl.addOperationOrder  - process finished");
    }

    @Override
    public Double sumTotalOperationCostByEquipment(String equipmentCode) {
        log.info("stage:init VesselAPI.someTotalOperationCostByEquipment equipmentCode:{}", equipmentCode);

        Double total = operationRepository.findAllByCode(equipmentCode).stream().mapToDouble(Operation::getCost).sum();

        log.info("stage:end VesselAPI.someTotalOperationCostByEquipment - process has been finished - totalCost:{}", total);
        return total;
    }

}
