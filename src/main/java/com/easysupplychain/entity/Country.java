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

    public void addPort(Port port) {
        ports.add(port);
        port.setCountry(this);
    }

    public void removePort(Port port) {
        ports.remove(port);
        port.setCountry(null);
    }

}
