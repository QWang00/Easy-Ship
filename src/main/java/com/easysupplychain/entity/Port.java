package com.easysupplychain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Port {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "closestPort", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shipper> shippers;

    public void addShipper(Shipper shipper) {
        shippers.add(shipper);
        shipper.setClosestPort(this);
    }

    public void removeShipper(Shipper shipper) {
        shippers.remove(shipper);
        shipper.setClosestPort(null);
    }

    public Port(String name) {
        this.name = name;
    }

}
