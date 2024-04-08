package com.easysupplychain.service;

import com.easysupplychain.entity.Container;
import com.easysupplychain.entity.Shipper;
import com.easysupplychain.repository.ContainerRepository;
import com.easysupplychain.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContainerService {
    @Autowired
    private ContainerRepository containerRepository;
    @Autowired
    ShipperRepository shipperRepository;

    public List<Container> findAllContainers() {
        return containerRepository.findAll();
    }

    public Container findContainerById(Long id) {
        Container container = containerRepository.findById(id).orElseThrow(() ->new RuntimeException("Container Not found"));
        return container;
    }
    @Transactional
    public void createContainer(Container container){
        containerRepository.save(container);
    }
    @Transactional
    public void updateContainer(Container container){
        containerRepository.save(container);
    }
    @Transactional
    public void deleteContainer(Long id){
        Container container = containerRepository.findById(id).orElseThrow(()-> new RuntimeException("Container Not Found"));
        containerRepository.deleteById(container.getId());
    }
    @Transactional
    public void createContainer(Container container, List<Long> shipperIds) {

        // Find shippers from the database
        List<Shipper> shippers = shipperRepository.findAllById(shipperIds);

        // Use the addShipper method to establish the relationship correctly
        shippers.forEach(container::addShipper);

        // Save the container, which will also persist the associations due to cascading
        containerRepository.save(container);
    }

    @Transactional
    public Container updateContainer(Container container, List<Long> shipperIds) {
        Container existingContainer = containerRepository.findById(container.getId())
                .orElseThrow(() -> new RuntimeException("Container not found"));

        // Update container properties
        existingContainer.setContainerNumber(container.getContainerNumber());
        existingContainer.setContainerSize(container.getContainerSize());
        existingContainer.setETD(container.getETD());
        existingContainer.setETA(container.getETA());
        existingContainer.setFromPort(container.getFromPort());
        existingContainer.setToPort(container.getToPort());
        existingContainer.setForwarder(container.getForwarder());

        // Remove the container from each shipper's set of containers
        if (existingContainer.getShippers() != null) {
            existingContainer.getShippers().forEach(shipper -> shipper.getContainers().remove(existingContainer));
        }

        // Clear current shippers if you're replacing them
        existingContainer.getShippers().clear();

        // If shipperIds is not null or empty, find new shippers from the database and re-establish the relationship
        if (shipperIds != null && !shipperIds.isEmpty()) {
            List<Shipper> shippers = shipperRepository.findAllById(shipperIds);
            shippers.forEach(existingContainer::addShipper); // This validates and adds shippers
        } else {
            throw new IllegalArgumentException("At least one shipper must be selected.");
        }
        // Save the updated container
        return containerRepository.save(existingContainer);
    }

    @Transactional
    public void deleteContainerWithShipper(Long id){
        Container container = containerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Container Not Found"));

        // Copy the shippers to avoid ConcurrentModificationException during iteration
        Set<Shipper> shippersCopy = new HashSet<>(container.getShippers());

        // Use the removeShipper method to disassociate each shipper
        for (Shipper shipper : shippersCopy) {
            container.removeShipper(shipper);
        }

        // Save the container after shipper removal to ensure changes are cascaded
        containerRepository.save(container);

        // Now we can safely delete the container
        containerRepository.deleteById(container.getId());

    }


}
