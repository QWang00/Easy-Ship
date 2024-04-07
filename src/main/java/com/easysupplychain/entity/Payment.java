package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_recipient_id")
    private PaymentRecipient paymentRecipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "container_id", nullable = false)
    private Container container;

    private String invoiceNumber;
    private BigDecimal amount;
    private Date paymentDate;
    private String remark;

    public Payment(String invoiceNumber, BigDecimal amount, String remark) throws ParseException {
        this.invoiceNumber = invoiceNumber;
        this.amount = new BigDecimal(String.valueOf(amount));
        this.remark = remark;
    }

    public String getRecipientType() {
        return this.getPaymentRecipient().getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    public Date calculateDueDate() {
        Calendar cal = Calendar.getInstance();
        if ("7 Days Before ETD".equals(paymentRecipient.getPaymentTerm())) {
            cal.setTime(container.getETD());
            cal.add(Calendar.DAY_OF_MONTH, -7);
        } else if ("Against Original B/L".equals(paymentRecipient.getPaymentTerm())) {
            cal.setTime(container.getETD());
            cal.add(Calendar.DAY_OF_MONTH, 7);
        } else if ("7 Days Before ETA".equals(paymentRecipient.getPaymentTerm())) {
            cal.setTime(container.getETA());
            cal.add(Calendar.DAY_OF_MONTH, -7);
        } else if ("End of Following Month".equals(paymentRecipient.getPaymentTerm())) {
            cal.setTime(container.getETA());
            cal.add(Calendar.MONTH, 1); // Move to the following month
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to the last day of the month
        } else {
            // Default case or unknown payment terms
            // You might want to handle this differently
            return null;
        }
        return cal.getTime();
    }

    public String calculatePaymentStatus() {
        Date now = new Date(); // Current date
        Date dueDate = calculateDueDate(); // Dynamically calculate due date

        if (paymentDate == null) {
            if (now.before(dueDate)) {
                return "Pending";
            } else {
                return "Overdue";
            }
        } else {
            if (paymentDate.before(dueDate) || paymentDate.equals(dueDate)) {
                return "Completed";
            } else {
                return "Paid Late";
            }
        }

    }




}
