package com.alex.buildindingAndco.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter// getter setter + toString + constructeur sans parametre
@AllArgsConstructor // constructeur avec parametres
@NoArgsConstructor // constructeur sans parametre
@Entity
@Table(name = "vehicles")
public class Vehicle {

    // attributs véhicule
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 7, nullable = false, unique = true)
    private String NumberPlate;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Long yearOfConstruction;
    // attribut en relation
    @OneToOne()
    private Technician technician;

}
