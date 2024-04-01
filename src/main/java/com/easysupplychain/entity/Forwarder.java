package com.easysupplychain.entity;

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
    @OneToMany(mappedBy = "forwarder")
    private Set<Container> containers = new HashSet<>();

    public Forwarder(Set<Container> containers) {
        this.containers = containers;
    }

    public String toString() {
        return "Forwarder{containers = " + containers + "}";
    }


}

