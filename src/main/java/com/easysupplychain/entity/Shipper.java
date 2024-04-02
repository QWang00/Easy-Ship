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
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "closest_port_id")
    private Port closestPort;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "container_shippers",
            joinColumns = @JoinColumn(name = "shipper_id"),
            inverseJoinColumns = @JoinColumn(name = "container_id")
    )
    private Set<Container> containers = new HashSet<>();



    public Shipper(String name, String currency, String paymentTerm, Port closestPort) {
        super(name, currency, paymentTerm);
        this.closestPort = closestPort;

    }

    public String toString() {
        return "Shipper{closestPort = " + closestPort + ", containers = " + containers + "}";
    }
}
