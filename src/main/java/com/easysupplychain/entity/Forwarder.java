package com.easysupplychain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("FORWARDER")
public class Forwarder extends PaymentRecipient {
    @OneToMany(mappedBy = "forwarder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Container> containers = new HashSet<>();

    public Forwarder(Set<Container> containers) {
        this.containers = containers;
    }

    public String toString() {
        return "Forwarder{containers = " + containers + "}";
    }

    public void addContainer(Container container) {
        containers.add(container);
        container.setForwarder(this);
    }

    public void removeContainer(Container container) {
        containers.remove(container);
        container.setForwarder(null);
    }

    public Forwarder(String name, String currency, String paymentTerm) {
        super(name, currency, paymentTerm);
    }

    @Override
    public String getType() {
        return "FORWARDER";
    }



}

