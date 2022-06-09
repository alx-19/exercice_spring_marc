package com.alex.buildindingAndco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data // getter setter + toString + constructeur sans parametre
@AllArgsConstructor // constructeur avec parametres
@NoArgsConstructor // constructeur sans parametre
@Entity
@Table(name = "managers")
public class Manager {

    // attributs manager
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(length = 15)
    private Long phone;
    @Column(nullable = false, length = 15)
    private Long mobile;
    // attribut en relation
    @OneToMany(mappedBy = "manager")
    Set<Technician> technicians;

}
