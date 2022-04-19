package com.shape.shapetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String code;

    @Column(length = 256, nullable = false)
    private String type;

    @Column(length = 12, precision = 2, nullable = false)
    private Float cost;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

}
