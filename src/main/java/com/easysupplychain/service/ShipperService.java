package com.easysupplychain.service;

import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.repository.ContainerRepository;
import com.easysupplychain.repository.ShipperRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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

//    @Transactional
//    public void associateShipperWithContainer(Long shipperId, List<Long> containerIds) {
//        Shipper shipper = shipperRepository.findById(shipperId)
//                .orElseThrow(() -> new RuntimeException("Shipper not found"));
//
//        // You can clear the existing containers if you're replacing them or skip this step if you're just adding to them
//        shipper.getContainers().clear();
//
//        // For each container ID, find the container and associate it with the shipper
//        for (Long containerId : containerIds) {
//            Container container = containerRepository.findById(containerId)
//                    .orElseThrow(() -> new RuntimeException("Container not found"));
//            shipper.addContainer(container);
//        }
//
//        // Saving the shipper should save the associations too, depending on your cascade configuration
//        shipperRepository.save(shipper);
//    }


}
