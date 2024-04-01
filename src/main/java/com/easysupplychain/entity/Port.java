package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "closestPort")
    private List<Shipper> shippers = new ArrayList<>();


    public Port(Long id, String name, Country country, List<Shipper> shippers) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.shippers = shippers;
    }

    public String toString() {
        return "Port{id = " + id + ", name = " + name + ", country = " + country + ", shippers = " + shippers + "}";
    }
}
