package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "recipient_id", nullable = false)
    private PaymentRecipient recipient;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    private String invoiceNumber;
    private BigDecimal amount;
    private Date paymentDate;
    private String remark;


    public Payment(Long id, PaymentRecipient recipient, Container container, String invoiceNumber, BigDecimal amount, Date paymentDate, String remark) {
        this.id = id;
        this.recipient = recipient;
        this.container = container;
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.remark = remark;
    }

    public String toString() {
        return "Payment{id = " + id + ", recipient = " + recipient + ", container = " + container + ", invoiceNumber = " + invoiceNumber + ", amount = " + amount + ", paymentDate = " + paymentDate + ", remark = " + remark + "}";
    }

    public String getRecipientType() {
        // This method assumes the discriminator values are "SHIPPER" and "FORWARDER"
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}
