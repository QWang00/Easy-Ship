package com.easysupplychain.repository;

import com.easysupplychain.entity.Shipper;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends PaymentRecipientRepository<Shipper> {

}
