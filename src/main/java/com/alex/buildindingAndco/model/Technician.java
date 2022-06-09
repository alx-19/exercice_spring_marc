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
@Table(name = "technicians")
public class Technician {

    // attributs technicien
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(length = 3)
    private Long age;

    // attribut en relation
    @ManyToOne()
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;
    @OneToOne()
    private Vehicle vehicle;
    @ManyToOne()
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "technician_worksite",
            joinColumns = {@JoinColumn(name = "technician_id")},
            inverseJoinColumns = {@JoinColumn(name = "worksite_id")}
    )
    Set<Worksite> worksites;

    // constructeur pour le mapper
    public Technician(Integer integer) {
        this.id = integer;
    }
}
