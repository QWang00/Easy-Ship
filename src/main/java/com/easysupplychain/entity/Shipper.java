package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("SHIPPER")
public class Shipper extends PaymentRecipient {
    @ManyToOne
    @JoinColumn(name = "closest_port_id")
    private Port closestPort;

    @ManyToMany
    @JoinTable(
            name = "container_shippers",
            joinColumns = @JoinColumn(name = "shipper_id"),
            inverseJoinColumns = @JoinColumn(name = "container_id")
    )
    private Set<Container> containers = new HashSet<>();


    public Shipper(Port closestPort, Set<Container> containers) {
        this.closestPort = closestPort;
        this.containers = containers;
    }

    public String toString() {
        return "Shipper{closestPort = " + closestPort + ", containers = " + containers + "}";
    }
}
