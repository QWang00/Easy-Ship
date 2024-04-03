package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_recipient_id")
    private PaymentRecipient paymentRecipient;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "container_id", nullable = false)
//    private Container container;

    private String invoiceNumber;
    private BigDecimal amount;
    private Date paymentDate;
    private String remark;

    public String getRecipientType() {
        // This method assumes the discriminator values are "SHIPPER" and "FORWARDER"
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    public Payment(String invoiceNumber, BigDecimal amount, LocalDate paymentDate, String remark) throws ParseException {
        this.invoiceNumber = invoiceNumber;
        this.amount = new BigDecimal(String.valueOf(amount));
        this.paymentDate = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(paymentDate));
        this.remark = remark;
    }
}
