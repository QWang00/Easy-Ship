package com.easysupplychain.service;

import com.easysupplychain.entity.Shipper;
import com.easysupplychain.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShipperService {
    @Autowired
    private ShipperRepository shipperRepository;

    public List<Shipper> findAllShippers() {
        return shipperRepository.findAll();
    }

    public Shipper findShipperById(Long id) {
        Shipper shipper = shipperRepository.findById(id).orElseThrow(() ->new RuntimeException("Shipper Not found"));
        return shipper;
    }

    public void createShipper(Shipper shipper){
        shipperRepository.save(shipper);
    }

    public void updateShipper(Shipper shipper){
        shipperRepository.save(shipper);
    }

    public void deleteShipper(Long id){
        Shipper shipper = shipperRepository.findById(id).orElseThrow(()-> new RuntimeException("Shipper Not Found"));
        shipperRepository.deleteById(shipper.getId());
    }
}
