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

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Port> ports = new ArrayList<>();

    public Country(String name) {
        this.name = name;
    }

    // Utility method to add a port to the country
    public void addPort(Port port) {
        ports.add(port);
        port.setCountry(this);
    }

    // Utility method to remove a port from the country
    public void removePort(Port port) {
        ports.remove(port);
        port.setCountry(null);
    }

    public String toString() {
        return "Country{id = " + id + ", name = " + name + "}";
    }
}
