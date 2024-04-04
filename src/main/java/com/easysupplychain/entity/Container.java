package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forwarder_id")
    private Forwarder forwarder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_port_id") // Arrival port for the container.
    private Port toPort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_port_id")
    private Port fromPort; // Departure port, shared by all shippers in this container.

    @ManyToMany(mappedBy = "containers",cascade = {CascadeType.PERSIST})
    private Set<Shipper> shippers = new HashSet<>();

//    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Payment> payments = new HashSet<>();


    private String containerNumber;
    private String containerSize;
    //private Integer cartonQty;
    private Date ETD;
    private Date ETA;


    public Container( String containerNumber, String containerSize, Date ETD, Date ETA) {
        this.containerNumber = containerNumber;
        this.containerSize = containerSize;
        //this.cartonQty = cartonQty;
        this.ETD = ETD;
        this.ETA = ETA;
    }



    // Method to get names of the first three shippers
    public String getFirstThreeShippers() {
        return shippers.stream()
                .map(Shipper::getName)
                .limit(3) // Limit to first three
                .collect(Collectors.joining(", ", "", shippers.size() > 3 ? "..." : ""));
        // Joins names with commas, appends "..." if more than three shippers
    }

    public void addShipper(Shipper shipper) {
        if (this.getShippers().isEmpty()) {
            this.setFromPort(shipper.getClosestPort());
        } else if (!shipper.getClosestPort().equals(this.getFromPort())) {
            throw new IllegalArgumentException("Shipper's closest port does not match the container's departure port."
                    + " Shipper's closest port: " + shipper.getClosestPort()
                    + ", Container's departure port: " + this.getFromPort());
        }
        // Ensure the relationship is maintained from both sides
        if (!this.getShippers().contains(shipper)) {
            this.getShippers().add(shipper);
            if (!shipper.getContainers().contains(this)) {
                shipper.getContainers().add(this);
            }
        }
    }

    public void removeShipper(Shipper shipper) {
        if (this.getShippers().contains(shipper)) {
            shippers.remove(shipper);
            if (shipper.getContainers().contains(this)) {
                shipper.getContainers().remove(this);
            }
        }
    }


//    public void addPayment(Payment payment) {
//        payments.add(payment);
//        payment.setContainer(this);
//    }
//
//    public void removePayment(Payment payment) {
//        payments.remove(payment);
//        payment.setContainer(null);
//    }

}
