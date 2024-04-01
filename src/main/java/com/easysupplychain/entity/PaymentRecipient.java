package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String currency;
    private String paymentTerm;


    public PaymentRecipient(String name, String currency, String paymentTerm) {

        this.name = name;
        this.currency = currency;
        this.paymentTerm = paymentTerm;
    }

    public String toString() {
        return "PaymentRecipient{id = " + id + ", name = " + name + ", currency = " + currency + ", paymentTerm = " + paymentTerm + "}";
    }
}

