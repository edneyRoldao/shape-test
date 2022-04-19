package com.shape.shapetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 8, nullable = false)
    private String code;

    @Column(length = 256, nullable = false)
    private String name;

    @Column(length = 256, nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "vessel_id", referencedColumnName = "id")
    private Vessel vessel;

}
