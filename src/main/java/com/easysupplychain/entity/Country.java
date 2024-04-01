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
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "country")
    private List<Port> ports = new ArrayList<>();


    public Country(Long id, String name, List<Port> ports) {
        this.id = id;
        this.name = name;
        this.ports = ports;
    }

    public String toString() {
        return "Country{id = " + id + ", name = " + name + ", ports = " + ports + "}";
    }
}
