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

    @ManyToOne
    @JoinColumn(name = "forwarder_id")
    private Forwarder forwarder;

    @ManyToOne
    @JoinColumn(name = "arrival_port_id")
    private Port arrivalPort;

    private String containerNumber;
    private String containerSize;
    private Integer cartonQty;
    private Date ETD;
    private Date ETA;

    @ManyToMany(mappedBy = "containers")
    private Set<Shipper> shippers = new HashSet<>();

    @OneToMany(mappedBy = "container")
    private Set<Payment> payments = new HashSet<>();


    public Container(Long id, Forwarder forwarder, Port arrivalPort, String containerNumber, String containerSize, Integer cartonQty, Date ETD, Date ETA, Set<Shipper> shippers) {
        this.id = id;
        this.forwarder = forwarder;
        this.arrivalPort = arrivalPort;
        this.containerNumber = containerNumber;
        this.containerSize = containerSize;
        this.cartonQty = cartonQty;
        this.ETD = ETD;
        this.ETA = ETA;
        this.shippers = shippers;
    }

    public String toString() {
        return "Container{id = " + id + ", forwarder = " + forwarder + ", arrivalPort = " + arrivalPort + ", containerNumber = " + containerNumber + ", containerSize = " + containerSize + ", cartonQty = " + cartonQty + ", ETD = " + ETD + ", ETA = " + ETA + ", shippers = " + shippers + "}";
    }
}
