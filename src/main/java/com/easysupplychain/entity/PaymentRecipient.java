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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)

public abstract class PaymentRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String currency;
    private String paymentTerm;

    @OneToMany(mappedBy = "paymentRecipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();

    public PaymentRecipient(String name, String currency, String paymentTerm) {
        this.name = name;
        this.currency = currency;
        this.paymentTerm = paymentTerm;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setPaymentRecipient(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setPaymentRecipient(null);
    }

    public String getType() {
        return "Unknown";
    }
}




