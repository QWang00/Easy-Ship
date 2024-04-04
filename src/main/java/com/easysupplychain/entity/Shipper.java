package com.easysupplychain.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closest_port_id")
    private Port closestPort;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "container_shippers",
            joinColumns = @JoinColumn(name = "shipper_id"),
            inverseJoinColumns = @JoinColumn(name = "container_id")
    )
    private Set<Container> containers = new HashSet<>();


    public Shipper(String name, String currency, String paymentTerm) {
        super(name, currency, paymentTerm);
    }

    public String toString() {
        return "Shipper{closestPort = " + closestPort + ", containers = " + containers + "}";
    }

    public void addContainer(Container container) {
        containers.add(container);
        container.getShippers().add(this);
    }

    public void removeContainer(Container container) {
        containers.remove(container);
        container.getShippers().remove(this);
    }

}
