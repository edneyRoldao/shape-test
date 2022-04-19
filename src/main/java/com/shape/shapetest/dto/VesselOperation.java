package com.shape.shapetest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VesselOperation {

    private String vesselCode;
    private Integer totalOperation;
    private Double averageOperation;

}
