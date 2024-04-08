package com.easysupplychain.service;

import com.easysupplychain.entity.Shipper;
import com.easysupplychain.repository.ContainerRepository;
import com.easysupplychain.repository.ShipperRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ShipperService {
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private ContainerRepository containerRepository;

    public List<Shipper> findAllShippers() {
        return shipperRepository.findAll();
    }

    public List<Shipper> findAllShippersByIds(List<Long> ids) {
        return shipperRepository.findAllById(ids);
    }

    public Shipper findShipperById(Long id) {
        Shipper shipper = shipperRepository.findById(id).orElseThrow(() ->new RuntimeException("Shipper Not found"));
        return shipper;
    }

    public Optional<Shipper> findShipperByIdOptional(Long id) {
        return shipperRepository.findById(id);
    }

    @Transactional
    public void createShipper(Shipper shipper){
        shipperRepository.save(shipper);
    }

    @Transactional
    public void updateShipper(Shipper shipper){
        shipperRepository.save(shipper);
    }

    @Transactional
    public void deleteShipper(Long id){
        Shipper shipper = shipperRepository.findById(id).orElseThrow(()-> new RuntimeException("Shipper Not Found"));
        shipperRepository.deleteById(shipper.getId());
    }

}
