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
        // Check if the container is already associated to prevent duplicate additions
        if (!this.containers.contains(container)) {
            containers.add(container);
            // Also make sure to update the other side of the relationship if it's not already done
            if (!container.getShippers().contains(this)) {
                container.getShippers().add(this);
                // Optionally set the container's fromPort if it's null and the shipper has a closestPort
                if (container.getFromPort() == null && this.getClosestPort() != null) {
                    container.setFromPort(this.getClosestPort());
                }
            }
        }
    }


    public void removeContainer(Container container) {
        // Check if the container is actually associated before attempting to remove
        if (this.containers.contains(container)) {
            containers.remove(container);
            // Ensure the other side of the relationship is also updated accordingly
            if (container.getShippers().contains(this)) {
                container.getShippers().remove(this);
            }
        }
    }


}
