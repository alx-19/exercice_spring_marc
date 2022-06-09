package com.alex.buildindingAndco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data // getter setter + toString + constructeur sans parametre
@AllArgsConstructor // constructeur avec parametres
@NoArgsConstructor // constructeur sans parametre
@Entity
@Table(name = "worksites")
public class Worksite {

    // attribut chantier
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "NUMERIC")
    private Double price;

    // attribut en relation
    @OneToOne()
    private Address address;
    @ManyToMany(mappedBy = "worksites")
    Set<Technician> technicians;

    // constructeur pour le mapper
    public Worksite(Integer integer) {
        this.id=integer;
    }
}
