package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

    @ManyToMany(mappedBy = "containers")
    private Set<Shipper> shippers = new HashSet<>();

//    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Payment> payments = new HashSet<>();


    private String containerNumber;
    private String containerSize;
    private Integer cartonQty;
    private Date ETD;
    private Date ETA;


    public Container( String containerNumber, String containerSize, Integer cartonQty, Date ETD, Date ETA) {
        this.containerNumber = containerNumber;
        this.containerSize = containerSize;
        this.cartonQty = cartonQty;
        this.ETD = ETD;
        this.ETA = ETA;

    }

    public String toString() {
        return "Container{id = " + id + ", forwarder = " + forwarder + ", toPort = " + toPort + ", containerNumber = " + containerNumber + ", containerSize = " + containerSize + ", cartonQty = " + cartonQty + ", ETD = " + ETD + ", ETA = " + ETA + ", shippers = " + shippers + "}";
    }

//    public void addShipper(Shipper shipper) {
//        shippers.add(shipper);
//        shipper.getContainers().add(this);
//    }

    public void addShipperToContainer(Container container, Shipper shipper) {
        if (container.getShippers().isEmpty()) {
            // If the container is new and has no shippers yet, set the fromPort based on the first shipper's closestPort
            container.setFromPort(shipper.getClosestPort());
        } else if (!shipper.getClosestPort().equals(container.getFromPort())) {
            // If the shipper's closestPort does not match the container's fromPort, throw an exception
            throw new IllegalArgumentException("Shipper's closest port does not match the container's departure port.");
        }
        // If the shipper's closestPort matches the container's fromPort, add the shipper to the container
        container.getShippers().add(shipper);
    }


    public void removeShipper(Shipper shipper) {
        shippers.remove(shipper);
        shipper.getContainers().remove(this);
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
