package com.alex.buildindingAndco.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Setter
@Getter// getter setter + toString + constructeur sans parametre
@AllArgsConstructor // constructeur avec parametres
@NoArgsConstructor // constructeur sans parametre
@Entity
@Table(name = "addresses")
public class Address {

    // attribut adresse
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 5, nullable = false)
    private Long number;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    // attribut en relation
    @OneToMany(mappedBy = "address")
    private Set<Technician> technicians;
    @OneToOne()
    private Worksite worksite;
}
