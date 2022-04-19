package com.shape.shapetest.service;

import com.shape.shapetest.model.Equipment;
import com.shape.shapetest.model.Operation;
import com.shape.shapetest.model.Vessel;

import java.util.List;

public interface VesselService {

    void insertVessel(Vessel vessel);

    void insertEquipmentToVessel(String vesselCode, Equipment equipment);

    List<String> disableEquipments(List<String> equipmentCodes);

    List<Equipment> findAllActiveEquipmentFromVessel(String vesselCode);

    void addOperationOrder(Operation operation);

    Double sumTotalOperationCostByEquipment(String equipmentCode);

}
